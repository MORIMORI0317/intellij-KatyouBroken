package net.morimori0317.katyoubroken;

import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KBVfsListener implements BulkFileListener {
    @Override
    public void before(@NotNull List<? extends VFileEvent> events) {
        for (VFileEvent event : events) {
            if (event instanceof VFileDeleteEvent) {
                KatyouPlayer.playBroken(true);
            }
        }
    }
}
