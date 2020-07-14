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
            if (sender instanceof Player) {
                if (args.length == 0) {
                    sender.sendMessage("/che <金額> : 所持金を消費して小切手を作成します" + "\n" + "/che o : 小切手を開封して現金を引き出します");
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
                            cheque(m, p, s);
                            return true;
                        }
                        sender.sendMessage("数字以外のものが入力されています");
                        return true;
                    }
                    sender.sendMessage("/che make <金額> : 無から金を生み出します" + "\n" + "悪用したらバレるで");
                }
                if (args[0].equals("o")) {
                    p = (Player) sender;
                    ItemStack item = p.getInventory().getItemInMainHand();
                    if (item.getType() == Material.STONE) {
                        boolean res2 = true;
                        String lore = "null";
                        try {
                            lore = item.getLore().get(1);
                        } catch (NullPointerException | IndexOutOfBoundsException e) {
                            sender.sendMessage("あなたが手に持っているのは小切手ではありません");
                            return true;
                        }
                        for (int i = 0; i < lore.length(); i++) {
                            if (Character.isDigit(lore.charAt(i))) {
                                continue;
                            } else {
                                res2 = false;
                                break;
                            }
                        }
                        if (res2) {
                            Player z = (Player) sender;
                            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                            vault.deposit(z.getUniqueId(), Integer.parseInt(lore));
                            sender.sendMessage("小切手を換金しました");
                            System.out.println("換金" + lore);
                            return true;
                        }
                    }
                    sender.sendMessage("あなたが手に持っているのは小切手ではありません");
                }
                boolean res3 = true;
                for (int i = 0; i < args[0].length(); i++) {
                    if (Character.isDigit(args[0].charAt(i))) {
                        continue;
                    } else {
                        res3 = false;
                        break;
                    }
                }
                if (res3) {
                    money = Integer.parseInt(args[0]);
                    if (vault.getBalance(sender.getUniqueId()) > money) {
                        p = (Player) sender;
                        s = "normal";
                        m = String.valueOf(money);
                        //所持金抜き取り
                        vault.withdraw(sender.getUniqueId(), money);
                        cheque(m, p, s);
                        sender.sendMessage("小切手を作成しました");
                        return true;
                    }
                    sender.sendMessage("所持金が足りません");
                    return true;
                }
            }
            if (args.length == 0) {
                sender.sendMessage("/che <プレイヤー> <金額> : 指定したプレイヤーに指定した金額の小切手を持たせます");
                return true;
            }
            Player g = getServer().getPlayer(args[0]);
            boolean res4 = true;
            for (int i = 0; i < args[1].length(); i++) {
                if (Character.isDigit(args[1].charAt(i))) {
                    continue;
                } else {
                    res4 = false;
                    break;
                }
            }
            if (res4) {
                int mo = Integer.parseInt(args[1]);
            }
            if (res4 && g != null) {
                s = "console";
                cheque("console",g,s);
            }
            return true;
        }
        return true;
    }

    public void cheque(String m , Player p, String s) {
        ItemStack item = new ItemStack(Material.STONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(p.getName(),m));//Loreセット
        meta.setDisplayName("小切手");//アイテム名セット
        item.setItemMeta(meta);
        p.getInventory().addItem(item);//アイテムを渡す
        p.sendMessage(m + "円の小切手を作成しました");
        System.out.println("小切手作成" + p.getName() + ":" + m + "手段" + s);
        return;
    }
}
