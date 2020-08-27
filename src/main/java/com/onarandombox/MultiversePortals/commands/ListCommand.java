/*
 * Multiverse 2 Copyright (c) the Multiverse Team 2011.
 * Multiverse 2 is licensed under the BSD License.
 * For more information please check the README.md file included
 * with this project
 */

package com.onarandombox.MultiversePortals.commands;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiversePortals.MVPortal;
import com.onarandombox.MultiversePortals.MultiversePortals;

public class ListCommand extends PortalPaginatedCommand {

    // https://stackoverflow.com/questions/7470861/return-multiple-values-from-a-java-method-why-no-n-tuple-objects
    private static class ValIndex {
       String val;
       int index;
    }

    private static ValIndex parseWorld(List<String> args) {
        world = this.plugin.getCore().getMVWorldManager().getMVWorld(args.get(args.size() - 1));
    }

    public ListCommand(MultiversePortals plugin) {
        super(plugin);
        this.setName("Portal Listing");
        this.setCommandUsage("/mvp list " + ChatColor.GOLD + "[FILTER] [WORLD]");
        this.setArgRange(0, 2);
        this.addKey("mvp list");
        this.addKey("mvpl");
        this.addKey("mvplist");
        this.setPermission("multiverse.portal.list", "Displays a listing of all portals that you can enter.", PermissionDefault.OP);
        this.setItemsPerPage(8);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        String world_arg = null;
        MultiverseWorld world = null;
        ValIndex world_arg = null;


        MultiverseWorld world2 = null;
        MultiverseWorld world3 = null;
        String filter = null;
        String filter1 = null;
        String filter2 = null;
        String filter3 = null;
        int page = 0;
        int page1 = 0;
        int page2 = 0;
        int page3 = 0;

        // if (args.size() == 1) {
        // } else if (args.size() == 2) {
        // } else if (args.size() == 3) {
        //     world_arg = args.get(args.size() - 1)
        //     world = this.plugin.getCore().getMVWorldManager().getMVWorld(args.get(args.size() - 1));
        //     filter = args.get(0);
        //     if (args.size() == 2 && world == null) {
        //         sender.sendMessage("Multiverse does not know about " + ChatColor.GOLD + args.get(1));
        //         return;
        //     } else if (world == null && filter == null) {
        //         sender.sendMessage("Multiverse does not know about " + ChatColor.GOLD + args.get(1));
        //         return;
        //     } else if (args.size() == 1 && world != null) {
        //         filter = null;
        //     }
        // }

        // First, see if argument is a world and world isn't set
        // Then, see if argument is a page and page isn't set
        // If not a page or world, it's a third argument, then it's a filter

        world_arg = parseWorld(args);
        page_arg = parsePage(args, world_arg);
        filter_arg = parseFilter(args, world_arg, page_arg);

        world = world_arg.val;
        page = page_arg.val;
        filter = filter_arg.val;

        if (arg1) {
            world1 = this.plugin.getCore().getMVWorldManager().getMVWorld(arg1);
            try {
                page1 = Integer.parseInt(arg1);
            } catch (NumberFormatException ex) {}
            filter1 = arg1;
        }

        if (arg2) {
            world2 = this.plugin.getCore().getMVWorldManager().getMVWorld(arg2);
            try {
                page2 = Integer.parseInt(arg2);
            } catch (NumberFormatException ex) {}
            filter2 = arg2;
        }

        if (arg3) {
            world3 = this.plugin.getCore().getMVWorldManager().getMVWorld(arg3);
            try {
                page3 = Integer.parseInt(arg3);
            } catch (NumberFormatException ex) {}
            filter3 = arg3;
        }

        if (arg3 && world1 == null && world2 == null && world3 == null) {
            sender.sendMessage("Invalid Multiverse world specified");
        }

        if (arg3 && page1 == 0 && page2 == 0 && page3 == 0) {
            sender.sendMessage("Invalid page number specified");
        }

        if (arg3 && filter1 == null && filter2 == null && filter3 == null) {
            sender.sendMessage("Invalid portal name filter specified");
        }

        if (world1) {
            world = world1;
        } else if (world2) {
            world = world2;
        } else if (world3) {
            world = world3;
        }

        if (world1) {
            world = world1;
        } else if (world2) {
            world = world2;
        } else if (world3) {
            world = world3;
        }




        if (arg2) {
            if (world == null) {
                world = this.plugin.getCore().getMVWorldManager().getMVWorld(arg2);
            }
            if (world == null) {
                if (page == null) {
                    try {
                        page = Integer.parseInt(arg2);
                    } catch (NumberFormatException ex) {
                        if (filter == null) {
                            filter = arg2;
                        }
                        page = 1;
                    }
                } else {

                }
            }
        }


        FilterObject filterObject = this.getPageAndFilter(args);
        String titleString = ChatColor.AQUA + "Portals";
        if (world != null) {
            titleString += " in " + ChatColor.YELLOW + world.getAlias();
        }
        if (filter != null) {
            titleString += ChatColor.GOLD + " [" + filter + "]";
        }
        sender.sendMessage(ChatColor.AQUA + "--- " + titleString + ChatColor.AQUA + " ---");

        if (filterObject.getFilter().length() > 0) {
            List<String> availablePortals = this.getAvailablePortals(sender, world);
            availablePortals = this.getFilteredItems(availablePortals, filterObject.getFilter());
            if (availablePortals.size() == 0) {
                sender.sendMessage(ChatColor.RED + "Sorry... " + ChatColor.WHITE
                        + "No worlds matched your filter: " + ChatColor.AQUA + filterObject.getFilter());
                return;
            }
        }

        // sender.sendMessage(getPortals(sender, world, filter));

        boolean altColor = false;

        // for (String s : getFilteredItems(sender, world, filter)) {
        // // for (String s : getFilteredPortals(sender, world, filter)) {
        //     if (altColor) {
        //         sender.sendMessage(ChatColor.YELLOW + s);
        //         altColor = false;
        //     } else {
        //         sender.sendMessage(ChatColor.WHITE + s);
        //         altColor = true;
        //     }
        // }
    }

    @Override
    protected List<String> getFilteredItems(List<String> availableItems, String filter) {


    }
    //     List<String> filtered = new ArrayList<String>();

    //     for (String s : availableItems) {
    //         if (s.matches("(?i).*" + filter + ".*")) {
    //             filtered.add(s);
    //         }
    //     }
    //     return filtered;
    // }
    private List<String> getAvailablePortals(CommandSender sender, MultiverseWorld world) {
        List<MVPortal> portals;
        if (world == null) {
            portals = this.plugin.getPortalManager().getPortals(sender);
        } else {
            portals = this.plugin.getPortalManager().getPortals(sender, world);
        }
        return portals;
    }


    private List<String> getFilteredItems(CommandSender sender, MultiverseWorld world, String filter) {
    // private List<String> getFilteredPortals(CommandSender sender, MultiverseWorld world, String filter) {
        List<String> portals_filtered = new ArrayList<>();
        List<MVPortal> portals;

        if (filter == null) {
            filter = "";
        }


        // if (world == null) {
        //     portals = this.plugin.getPortalManager().getPortals(sender);
        // } else {
        //     portals = this.plugin.getPortalManager().getPortals(sender, world);
        // }

        for (MVPortal p : portals) {
            String name = p.getName();
            if (name.matches("(i?).*" + filter + ".*")) {
                portals_filtered.add(name);
            }
        }

        portals_filtered.sort(Comparator.comparing(String::toString));
        return portals_filtered;
    }
}
