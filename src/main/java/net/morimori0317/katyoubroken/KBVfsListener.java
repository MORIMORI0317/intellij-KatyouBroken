package net.morimori0317.katyoubroken;

import com.intellij.openapi.vfs.AsyncFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KBVfsListener implements AsyncFileListener {
    @Override
    public @Nullable ChangeApplier prepareChange(@NotNull List<? extends @NotNull VFileEvent> events) {
        boolean[] broken = new boolean[1];
        for (VFileEvent event : events) {
            if (event instanceof VFileDeleteEvent && !event.isFromRefresh()) {
                broken[0] = true;
                break;
            }
        }

        return new ChangeApplier() {
            @Override
            public void afterVfsChange() {
                if (broken[0]) KatyouPlayer.getInstance().playBroken(true);
            }
        };
    }
}
