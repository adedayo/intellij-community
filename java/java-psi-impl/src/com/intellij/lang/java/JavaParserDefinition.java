/*
 * Copyright 2000-2009 JetBrains s.r.o.
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
package com.intellij.lang.java;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.JavaLexer;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LanguageLevelProjectExtension;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.JavaStubElementType;
import com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.impl.source.tree.JavaElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/**
 * @author max
 */
public class JavaParserDefinition implements ParserDefinition {
  @NotNull
  public Lexer createLexer(final Project project) {
    final LanguageLevel languageLevel = LanguageLevelProjectExtension.getInstance(project).getLanguageLevel();
    return createLexer(languageLevel);
  }

  @NotNull
  public static Lexer createLexer(final LanguageLevel languageLevel) {
    return new JavaLexer(languageLevel);
  }

  public IFileElementType getFileNodeType() {
    return JavaStubElementTypes.JAVA_FILE;
  }

  @NotNull
  public TokenSet getWhitespaceTokens() {
    return ElementType.JAVA_WHITESPACE_BIT_SET;
  }

  @NotNull
  public TokenSet getCommentTokens() {
    return ElementType.JAVA_COMMENT_BIT_SET;
  }

  @NotNull
  public TokenSet getStringLiteralElements() {
    return TokenSet.create(JavaElementType.LITERAL_EXPRESSION);
  }

  @NotNull
  public PsiParser createParser(final Project project) {
    throw new UnsupportedOperationException("Should not be called directly");
  }

  @NotNull
  public PsiElement createElement(final ASTNode node) {
    final IElementType type = node.getElementType();
    if (type instanceof JavaStubElementType) {
      return ((JavaStubElementType)type).createPsi(node);
    }

    throw new IllegalStateException("incorrect element type for JavaParserDefinition: " + type);
  }

  public PsiFile createFile(final FileViewProvider viewProvider) {
    return new PsiJavaFileImpl(viewProvider);
  }

  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    final PsiFile containingFile = left.getTreeParent().getPsi().getContainingFile();
    final Lexer lexer;
    if(containingFile instanceof PsiJavaFile)
      lexer = new JavaLexer(((PsiJavaFile)containingFile).getLanguageLevel());
    else lexer = new JavaLexer(LanguageLevel.HIGHEST);
    if(right.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) return SpaceRequirements.MUST_NOT;
    if(left.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) return SpaceRequirements.MUST_NOT;
    final SpaceRequirements spaceRequirements = LanguageUtil.canStickTokensTogetherByLexer(left, right, lexer);
    if(left.getElementType() == JavaTokenType.END_OF_LINE_COMMENT) return SpaceRequirements.MUST_LINE_BREAK;

    if(left.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
      String text = left.getText();
      if (text.length() > 0 && Character.isWhitespace(text.charAt(text.length() - 1))) {
        return SpaceRequirements.MAY;
      }
    }

    if(right.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
      String text = right.getText();
      if (text.length() > 0 && Character.isWhitespace(text.charAt(0))) {
        return SpaceRequirements.MAY;
      }
    }
    else if (right.getElementType() == JavaDocTokenType.DOC_INLINE_TAG_END) {
      return SpaceRequirements.MAY;
    }

    return spaceRequirements;
  }
}
