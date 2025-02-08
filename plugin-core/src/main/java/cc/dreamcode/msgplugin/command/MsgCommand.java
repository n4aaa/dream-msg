package cc.dreamcode.msgplugin.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.*;
import cc.dreamcode.msgplugin.config.MessageConfig;
import cc.dreamcode.msgplugin.config.PluginConfig;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;

@Command(name = "message", aliases = "msg")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MsgCommand implements CommandBase {

    private final MessageConfig messageConfig;

    @Async
    @Executor(description = "Wysyła wiadomość do gracza.")
    @Completion(arg="target", value={"@allplayers"})
    public void msg(Player player, @Arg Player target, @Args(min = 1) String[] message) {
        if (player != target) {
            this.messageConfig.privateMessage.send(target, new MapBuilder<String, Object>()
                    .put("player", player.getName())
                    .put("target", target.getName())
                    .put("message", String.join(" ", message)).build()
            );
            this.messageConfig.privateMessage.send(player, new MapBuilder<String, Object>()
                    .put("player", player.getName())
                    .put("target", target.getName())
                    .put("message", String.join(" ", message)).build()
            );

            return;
        }

        this.messageConfig.ambigousPlayer.send(player);

        return;
    }
}