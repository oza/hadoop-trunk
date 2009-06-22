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

package org.apache.hadoop.record.compiler;

import org.apache.hadoop.record.compiler.JType.CType;
import org.apache.hadoop.record.compiler.JType.CppType;

/**
 * Code generator for "long" type
 */
public class JLong extends JType {
  
  class JavaLong extends JavaType {
    
    JavaLong() {
      super("long", "Long", "Long");
    }
    
    void genHashCode(CodeBuffer cb, String fname) {
      cb.append("ret = (int) ("+fname+"^("+fname+">>>32));\n");
    }
    
    void genSlurpBytes(CodeBuffer cb, String b, String s, String l) {
      cb.append("{\n");
      cb.append("long i = org.apache.hadoop.record.Utils.readVLong("+b+", "+s+");\n");
      cb.append("int z = org.apache.hadoop.record.Utils.getVIntSize(i);\n");
      cb.append(s+"+=z; "+l+"-=z;\n");
      cb.append("}\n");
    }
    
    void genCompareBytes(CodeBuffer cb) {
      cb.append("{\n");
      cb.append("long i1 = org.apache.hadoop.record.Utils.readVLong(b1, s1);\n");
      cb.append("long i2 = org.apache.hadoop.record.Utils.readVLong(b2, s2);\n");
      cb.append("if (i1 != i2) {\n");
      cb.append("return ((i1-i2) < 0) ? -1 : 0;\n");
      cb.append("}\n");
      cb.append("int z1 = org.apache.hadoop.record.Utils.getVIntSize(i1);\n");
      cb.append("int z2 = org.apache.hadoop.record.Utils.getVIntSize(i2);\n");
      cb.append("s1+=z1; s2+=z2; l1-=z1; l2-=z2;\n");
      cb.append("}\n");
    }
  }
  /** Creates a new instance of JLong */
  public JLong() {
    setJavaType(new JavaLong());
    setCppType(new CppType("int64_t"));
    setCType(new CType());
  }
  
  String getSignature() {
    return "l";
  }
}