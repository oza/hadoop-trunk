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

package org.apache.hadoop.yarn.api.records;

import java.text.NumberFormat;

public abstract class ApplicationAttemptId implements
    Comparable<ApplicationAttemptId> {
  public abstract ApplicationId getApplicationId();
  public abstract int getAttemptId();
  
  public abstract void setApplicationId(ApplicationId appID);
  public abstract void setAttemptId(int attemptId);

  
  
  protected static final NumberFormat idFormat = NumberFormat.getInstance();
  static {
    idFormat.setGroupingUsed(false);
    idFormat.setMinimumIntegerDigits(4);
  }

  protected static final NumberFormat counterFormat = NumberFormat
      .getInstance();
  static {
    counterFormat.setGroupingUsed(false);
    counterFormat.setMinimumIntegerDigits(6);
  }

  @Override
  public int hashCode() {
    // Generated by eclipse.
    final int prime = 31;
    int result = 1;
    ApplicationId appId = getApplicationId();
    result = prime * result + ((appId == null) ? 0 : appId.hashCode());
    result = prime * result + getAttemptId();
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (other.getClass().isAssignableFrom(this.getClass())) {
      ApplicationAttemptId otherAttemptId = (ApplicationAttemptId) other;
      if (this.getApplicationId().equals(otherAttemptId.getApplicationId())) {
        return this.getAttemptId() == otherAttemptId.getAttemptId();
      }
    }
    return false;
  }

  @Override
  public int compareTo(ApplicationAttemptId other) {
    int compareAppIds = this.getApplicationId().compareTo(
        other.getApplicationId());
    if (compareAppIds == 0) {
      return this.getAttemptId() - other.getAttemptId();
    } else {
      return compareAppIds;
    }
  }
  
  @Override
  public String toString() {
    String id =
        (this.getApplicationId() != null) ? this.getApplicationId()
            .getClusterTimestamp()
            + "_"
            + idFormat.format(this.getApplicationId().getId()) : "none";
    return "appattempt_" + id + "_" + counterFormat.format(getAttemptId());
  }
}