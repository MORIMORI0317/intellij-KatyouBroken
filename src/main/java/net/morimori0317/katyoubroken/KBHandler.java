package net.morimori0317.katyoubroken;

import com.intellij.codeInsight.editorActions.BackspaceHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class KBHandler extends BackspaceHandlerDelegate {
    private static final Random rand = new Random();

    @Override
    public void beforeCharDeleted(char c, @NotNull PsiFile psiFile, @NotNull Editor editor) {

    }

    @Override
    public boolean charDeleted(char c, @NotNull PsiFile psiFile, @NotNull Editor editor) {
        if (rand.nextInt(10) == 0)
            KatyouPlayer.playBroken(false);
        return false;
    }
}
