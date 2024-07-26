package net.luuh.test.modules.itemeditor.commands;

import net.kyori.adventure.text.Component;
import net.luuh.test.Helper;
import net.luuh.test.abstraction.modules.CommandSuccessException;
import net.luuh.test.abstraction.modules.ModuleCommand;
import net.luuh.test.constants.Permission;
import net.luuh.test.modules.itemeditor.ItemEditor;
import net.luuh.test.utils.RMUtils;
import net.luuh.test.utils.Util;
import net.luuh.test.utils.color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ItemEditorCommand extends ModuleCommand<ItemEditor> {

    public ItemEditorCommand(Helper helper, ItemEditor module) {
        super(helper, module, "itemeditor", "ieditor");
    }

    @Override
    protected void execute(Player player, String[] args) throws CommandSuccessException {
        if(!Permission.ITEM_EDITOR.has(player)) return;
        if(!(args.length > 0)) {

            player.sendMessage(helper.getRMUtils().readTranslation("item-editor-help-announce"));


        } else {

            if(args[1] == null && !Permission.ITEM_EDITOR.has(player)) return;
            if(player.getInventory().getItemInMainHand().isEmpty()) return;
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = item.getItemMeta();
            switch (args[0].toLowerCase()) {


                case "rename":
                    if(!Permission.ITEM_EDITOR_RENAME.has(player)) return;
                    if(args[1] == null) return;


                    itemMeta.displayName(color.format(args[1]));
                    item.setItemMeta(itemMeta);
                    player.getInventory().setItemInMainHand(item);
                    player.sendMessage(helper.getRMUtils().readTranslation("item-editor-rename-success"));
                    break;

                case "lore":
                    if(!Permission.ITEM_EDITOR_LORE.has(player)) return;
                    if(!itemMeta.hasLore()) {
                        itemMeta.setLore(Arrays.asList(""));
                    }
                    if(args[1] == null) for (Component c : item.getItemMeta().lore()) {
                        player.sendMessage(c);
                    } else {
                        switch (args[1].toLowerCase()){
                            case "add":
                                if(args[2] == null) return;

                                item.setItemMeta(itemMeta);
                                player.getInventory().setItemInMainHand(item);
                                player.sendMessage(helper.getRMUtils().readTranslation("item-editor-lore-add-success"));
                                break;
                            case "remove":
                                if(args[2] == null) return;
                                if(!Util.isInt(args[2])) return;

                                itemMeta.lore().remove(args[2]);
                                item.setItemMeta(itemMeta);
                                player.getInventory().setItemInMainHand(item);
                                player.sendMessage(helper.getRMUtils().readTranslation("item-editor-lore-remove-success"));
                                break;
                            case "set":
                                if(args[2] == null || args[3] == null) return;
                                if(!Util.isInt(args[2])) return;
                                itemMeta.lore().set(Integer.parseInt(args[2]), color.format(args[3]));
                                item.setItemMeta(itemMeta);
                                player.getInventory().setItemInMainHand(item);
                                player.sendMessage(helper.getRMUtils().readTranslation("item-editor-lore-set-success"));
                                break;
                        }
                    }
                    break;
                case "custommodeldata":
                    if(args[1] == null) return;
                    if(!Util.isInt(args[1])) return;
                    itemMeta.setCustomModelData(Integer.parseInt(args[1]));
                    item.setItemMeta(itemMeta);
                    player.getInventory().setItemInMainHand(item);
                    player.sendMessage(helper.getRMUtils().readTranslation("item-editor-custommodeldata-success"));
                    break;





            }
        }
    }
}
