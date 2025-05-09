# Minecraft WBTC Plugin

A simple Minecraft survival enhancement plugin designed for Bukkit/Paper servers (API version 1.21+). WBTC adds several quality-of-life features and commands to improve the vanilla survival experience for small communities.


## Features

*   __Friend System:__ Manage friends, send/receive requests.
*   __Ender Chest Access:__ Open your own or a friend's ender chest anywhere.
*   __Gamemode Switching:__ Quickly change your gamemode.
*   __Position Sharing:__ Share your coordinates with friends or request theirs.
*   __Ping Check:__ Check your latency to the server.
*   __Waypoints:__ Set, list, remove, and view personal waypoints.
*   __One Player Sleep:__ Skips the night if at least one player sleeps.
*   __Database:__ Uses local JSON files to persist data. No external SQL database ist required.
*   __Maybe more to come...__ If got time and ideas i might add more features in the future.


## Installation

There are two ways to install the WBTC plugin:

__1. Using the pre-built JAR (Recommended):__

*   Go to the [Releases page](https://github.com/julian-bruyers/mc-wbtc/releases) of the WBTC GitHub repository.
*   Download the latest `wbtc.jar` file.
*   Place the downloaded `wbtc.jar` file into the `plugins` folder of your Minecraft server.
*   Restart or reload your server.

__2. Using a self-compiled JAR:__

*   Follow the instructions in the [Building](#building) section to compile the plugin yourself.
*   Navigate to the `target/` directory within the project folder.
*   Copy the generated `.jar` file (e.g., `WBTC-1.0.0-BETA.jar`).
*   Place the copied `.jar` file into the `plugins` folder of your Minecraft server.
*   Restart or reload your server.


## Commands

Here is a list of all available commands:

`/wbtc (Alias: /wbtcinfo)`

__Description:__ Get information about the plugin.

__Usage:__ /wbtc


`/friend`

__Description:__ Manage your friends.

__Usage:__ /friend <add|remove|accept|deny> <player> or /friend <list|requests> (Use @a for <player> to target all players for add/accept/deny/remove actions).


`/enderchest (Alias: /ec)`

__Description:__ Open your ender chest or a friend's ender chest.

__Usage:__ /enderchest (<player>)


`/gm`

__Description:__ Change your gamemode.

__Usage:__ /gm <0|1|2|3> (0=Survival, 1=Creative, 2=Adventure, 3=Spectator)


`/position (Alias: /pos)`

__Description:__ Broadcast your position to friends or get a friend's position.

__Usage:__ /position (broadcasts) or /position <player> (gets position)


`/ping`

__Description:__ Get your current ping to the server.

__Usage:__ /ping


`/waypoint (Alias: /wp)`

__Description:__ Manage your waypoints.

__Usage:__ /waypoint <list|add|remove|show> (<waypoint name>)


## Permissions

These are the permissions required to use the plugin's features:

`wbtc.info`

__Description:__ Permission to use the full info command.


`wbtc.friend`

__Description:__ Permission to use the friend command.


`wbtc.enderchest.self`

__Description:__ Permission to open your own enderchest.


`wbtc.enderchest.friends`

__Description:__ Permission to open other players' ender chests (requires friendship).


`wbtc.enderchest.other`

__Description:__ Permission to open other players' ender chests.


`wbtc.gamemode.survival`

__Description:__ Permission to change your gamemode to survival.


`wbtc.gamemode.creative`

__Description:__ Permission to change your gamemode to creative.


`wbtc.gamemode.adventure`

__Description:__ Permission to change your gamemode to adventure.


`wbtc.gamemode.spectator`

__Description:__ Permission to change your gamemode to spectator.


`wbtc.position.self`

__Description:__ Permission to broadcast your position and use the base position command.


`wbtc.position.friends`

__Description:__ Permission to request the position of another player (requires friendship).


`wbtc.position.other`

__Description:__ Permission to request the position of another player.


`wbtc.ping`

__Description:__ Permission to use the ping command.


`wbtc.waypoint`

__Description:__ Permission to use the waypoint command.


## Assigning Permissions with LuckyPerms

To manage these permissions effectively, we recommend using a permissions plugin like [LuckyPerms](https://luckperms.net/). Here's how you can assign permissions using LuckyPerms commands:

1.  __Install LuckyPerms:__ Download and install the LuckyPerms plugin on your server.
2.  __Assign Permission to a Player:__
    ```bash
    /lp user <username> permission set <permission.node> true
    ```
    *Example:* To allow player `Steve` to use the `/friend` command:
    ```bash
    /lp user Steve permission set wbtc.friend true
    ```
3.  __Assign Permission to a Group:__
    ```bash
    /lp group <groupname> permission set <permission.node> true
    ```
    *Example:* To allow the `default` group to use the `/ping` command:
    ```bash
    /lp group default permission set wbtc.ping true
    ```
4.  __Granting All Gamemode Permissions:__ You might want to grant all gamemode permissions at once using a wildcard (if supported by your setup):
    ```bash
    /lp group <groupname> permission set wbtc.gamemode.* true
    ```
    *Example:* To allow the `admin` group to use all `/gm` modes by setting them individually:
    ```bash
    /lp group admin permission set wbtc.gamemode.survival true
    ```
    ```bash
    /lp group admin permission set wbtc.gamemode.creative true
    ```
    ```bash
    /lp group admin permission set wbtc.gamemode.adventure true
    ```
    ```bash
    /lp group admin permission set wbtc.gamemode.spectator true
    ```

Refer to the [LuckyPerms Command Usage documentation](https://luckperms.net/wiki/Command-Usage) for more detailed information and advanced features.


## Building

This project uses Apache Maven to manage dependencies and build the plugin `.jar` file. To compile the plugin yourself, follow these steps:

1.  __Install Prerequisites:__
    *   __Java Development Kit (JDK):__ Ensure you have JDK 17 or a higher version installed. You can download it from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/). Verify your installation by running `java -version` in your terminal.
    *   __Apache Maven:__ Download and install Apache Maven from the [official Maven website](https://maven.apache.org/download.cgi). Follow their installation guide for your operating system. Verify your installation by running `mvn -version` in your terminal.

2.  __Clone the Repository (Optional):__ If you haven't already, clone this repository to your local machine using Git:
    ```bash
    git clone https://github.com/julian-bruyers/mc-wbtc.git
    cd mc-wbtc
    ```

3.  __Navigate to Project Directory:__ Open your terminal or command prompt and navigate to the root directory of the cloned project (the folder containing the `pom.xml` file).

4.  __Run Maven Package Command:__ Execute the following Maven command. This will download dependencies, compile the source code, and package it into a `.jar` file.
    ```bash
    mvn package
    ```

5.  __Locate the JAR File:__ After the build process completes successfully, the compiled plugin `.jar` file (e.g., `WBTC-1.0.0-BETA.jar`) will be located in the `target/` directory within the project folder.

6.  __Install the Plugin:__ Copy this `.jar` file to the `plugins` folder of your Minecraft server and restart or reload the server.


## Contributing & Feature Requests

This is a private project primarily for personal use. Therefore, __no feature requests or pull requests will be accepted__. Active development on this public repository is not planned.

If you wish to make changes or add features, please fork the repository and maintain your own version.


## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for further Details.
