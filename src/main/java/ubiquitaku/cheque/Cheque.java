package ubiquitaku.cheque;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Cheque extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("che")) {
            String s;
            String m;
            int money;
            Player p;
            if (args[0].equals("o")) {
                //open
            }
            if (sender.isOp()) {
                if (args[0].equals("make")) {
                    boolean res1 = true;
                    for (int i = 0; i < args[1].length(); i++) {
                        if (Character.isDigit(args[1].charAt(i))) {
                            continue;
                        } else {
                            res1 = false;
                            break;
                        }
                    }
                    if (res1) {
                        money = Integer.parseInt(args[0]);
                        s = "make";
                        p = (Player) sender;
                        m = String.valueOf(money);
                        cheque(m,p,s);
                        return true;
                    }
                    sender.sendMessage("数字以外のものが入力されています");
                    return true;
                }
                sender.sendMessage("/che make <金額> : 無から金を生み出します" + "\n" + "悪用したらバレるで");
            }
            if (args[0].equals("o")) {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (item.getType() == Material.STONE) {
                    try {
                        String lore = item.getLore().get(1);
                        boolean result_2 = true;
                        for (int i = 0; i < lore.length(); i++) {
                            if (Character.isDigit(lore.charAt(i))) {
                                continue;
                            } else {
                                result_2 = false;
                                break;
                            }
                        }
                    } catch (知るかボケ lore) {
                        sender.sendMessage("あなたが手に持っているのは小切手ではありません");
                    }
                }
            }
            boolean res2 = true;
            for (int i = 0; i < args[0].length(); i++) {
                if (Character.isDigit(args[0].charAt(i))) {
                    continue;
                } else {
                    res2 = false;
                    break;
                }
            }
            if (res2) {
                money = Integer.parseInt(args[0]);
                if (所持金 > money){
                    p = (Player) sender;
                    s = "normal";
                    //所持金抜き取り
                    m = String.valueOf(money);
                    cheque(m,p,s);
                    return true;
                }
            }
        }
        return true;
    }

    public void cheque(String m , Player p, String s) {
        ItemStack item = new ItemStack(Material.STONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(p.getName(),m + ""));//Loreセット
        meta.setDisplayName("小切手");//アイテム名セット
        item.setItemMeta(meta);
        p.getInventory().addItem(item);//アイテムを渡す
        p.sendMessage(m + "円の小切手を作成しました");
        System.out.println("小切手作成" + p.getName() + ":" + m + "手段" + s);
        return;
    }
}
