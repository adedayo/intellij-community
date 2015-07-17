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
package com.intellij.codeInsight.daemon.quickFix;

import com.intellij.codeInsight.daemon.QuickFixActionRegistrar;
import com.intellij.codeInsight.daemon.impl.quickfix.OrderEntryFix;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.LibraryOrderEntry;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.testIntegration.JavaTestFramework;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * {@link MissingDependencyFixProvider} extension can be used to override or complement default platform {@link OrderEntryFix}-es.
 * <p/>
 * It can be useful for modules imported from external build system like Maven, Gradle etc,
 * when external build configuration(pom.xml/*.gradle scripts) should be changed in additional or instead of IntelliJ project configuration.
 *
 * @author Vladislav.Soroka
 * @since 7/15/2015
 */
public abstract class MissingDependencyFixProvider {
  public static final ExtensionPointName<MissingDependencyFixProvider> EP_NAME =
    ExtensionPointName.create("com.intellij.codeInsight.missingDependencyFixProvider");

  @Nullable
  public List<LocalQuickFix> registerFixes(@NotNull QuickFixActionRegistrar registrar, @NotNull final PsiReference reference) {
    return null;
  }

  @Nullable
  public OrderEntryFix getJUnitFix(@NotNull PsiReference reference,
                                   @NotNull OrderEntryFix platformFix,
                                   @NotNull Module currentModule,
                                   @NotNull JavaTestFramework framework,
                                   @NotNull String className) {
    return null;
  }

  @Nullable
  public OrderEntryFix getJetbrainsAnnotationFix(@NotNull PsiReference reference,
                                                 @NotNull OrderEntryFix platformFix,
                                                 @NotNull Module currentModule) {
    return null;
  }

  @Nullable
  public OrderEntryFix getAddModuleDependencyFix(@NotNull PsiReference reference,
                                                 @NotNull OrderEntryFix platformFix,
                                                 @NotNull Module currentModule,
                                                 @NotNull VirtualFile classVFile,
                                                 @NotNull PsiClass[] classes) {
    return null;
  }

  @Nullable
  public OrderEntryFix getAddLibraryToClasspathFix(@NotNull PsiReference reference,
                                                   @NotNull OrderEntryFix platformFix,
                                                   @NotNull Module currentModule,
                                                   @NotNull LibraryOrderEntry libraryEntry,
                                                   @NotNull PsiClass aClass) {
    return null;
  }

  @NotNull
  public Boolean addJarsToRoots(@NotNull List<String> jarPaths, @Nullable String libraryName,
                                @NotNull Module module, @Nullable PsiElement location) {
    return Boolean.FALSE;
  }
}
