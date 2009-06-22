/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.mapred.lib.aggregate;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * This class implements the generic combiner of Aggregate.
 */
public class ValueAggregatorCombiner extends ValueAggregatorJobBase {

  /**
   * Combiner does not need to configure.
   */
  public void configure(JobConf job) {

  }

  /** Combines values for a given key.  
   * @param key the key is expected to be a Text object, whose prefix indicates
   * the type of aggregation to aggregate the values. 
   * @param values the values to combine
   * @param output to collect combined values
   */
  public void reduce(WritableComparable key, Iterator values,
                     OutputCollector output, Reporter reporter) throws IOException {
    String keyStr = key.toString();
    int pos = keyStr.indexOf(ValueAggregatorDescriptor.TYPE_SEPARATOR);
    String type = keyStr.substring(0, pos);
    ValueAggregator aggregator = ValueAggregatorBaseDescriptor
      .generateValueAggregator(type);
    while (values.hasNext()) {
      aggregator.addNextValue(values.next());
    }
    Iterator outputs = aggregator.getCombinerOutput().iterator();

    while (outputs.hasNext()) {
      Object v = outputs.next();
      if (v instanceof Text) {
        output.collect(key, (Text)v);
      } else {
        output.collect(key, new Text(v.toString()));
      }
    }
  }

  /** 
   * Do nothing. 
   *
   */
  public void close() throws IOException {

  }

  /** 
   * Do nothing. Should not be called. 
   *
   */
  public void map(WritableComparable arg0, Writable arg1, OutputCollector arg2,
                  Reporter arg3) throws IOException {
    throw new IOException ("should not be called\n");
  }
}