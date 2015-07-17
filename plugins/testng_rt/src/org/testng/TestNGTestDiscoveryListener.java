/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.testng;

import com.intellij.execution.TestDiscoveryListener;

public class TestNGTestDiscoveryListener extends TestDiscoveryListener implements IDEATestNGListener {
  public void onTestStart(ITestResult result) {
    testStarted(result.getTestClass().getName(), result.getTestName());
  }

  public void onTestSuccess(ITestResult result) {
    onTestEnded(result);
  }

  public void onTestFailure(ITestResult result) {
    onTestEnded(result);
  }

  public void onTestSkipped(ITestResult result) {
    onTestEnded(result);
  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    onTestEnded(result);
  }

  public void onStart(ITestContext context) {}

  public void onFinish(ITestContext context) {}

  private void onTestEnded(ITestResult result) {
    testFinished(result.getTestClass().getName(), result.getName());
  }
}
