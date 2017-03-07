# Overall Procedure
1. Go through "Project Setup With Vagrant"
2. Hybris developers, your day-to-day work is accelerated using "Project Setup Without Vagrant".
3. Go through "Hybris User Setup"
4. Final sections provide helpful tips for building and running hybris, use them if they apply to you.

Project Setup With Vagrant
------------------
There will be two installations you can do, with and without vagrant. The vagrant installation is currently much slower, but better for standardization and testing.

NOTE: QA/Testers are required to test everything in Vagrant. It is therefore best practice for all developers to complete the Vagrant install once so they have it at hand.

1. Ensure that the latest version of VirtualBox is installed on your local environment.
2. Ensure that the latest version of Vagrant is installed on your local environment.
3. Download the Hybris bundle to the ./hybris-deployment/hybris-install directory:
   <https://drive.google.com/file/d/0B0vMh7bVp28fTnZRd1BYVndBczQ/view?usp=sharing>
4. From the ./hybris-deployment directory, start your Vagrant instance.
   
    ```
    vagrant up
    ```
   
5. If you're not already logged into the FM network, log into the FM VPN.
6. Log into your Vagrant instance.
    
    ```
    vagrant ssh
    ```
    
7. Build Hybris

    ```    
      cd /usr/share/hybris/bin/platform
    ```

    ```
     . ./setantenv.sh
    ```

   - Note that I've seen the "setantenv" fail before, so if you see a complaint on that or the next step, run it again.

    ```
    ant clean all
    ```

8. Run Hybris

    ```
    sh hybrisserver.sh
    ```

9. Wait for the Hybris server to start (this will take a few minutes). You will know it has started when your console window reads something like "INFO: Server startup in 167766 ms".

Project Setup Without Vagrant
------------------
The advantage of this approach is that Hybris starts up and performs faster (order of magnitude faster). The downside of this approach is that you will have to manage multiple Java installations and hybris will force its version of ant.

1. If your hybris vagrant box was running, stop the process and exit vagrant.

    - Stop hybris

      ```
     Control-C (to stop vagrant)
      ```

    - Exit vagrant

      ```
      exit
      ```

    - Halt vagrant box (stop vagrant, clear any port forwarding)

      ```
      vagrant halt
      ```

2. Ensure that Java 7 and 8 are installed on your local environment, and set 7 as default.
    - Get a list of available versions

      ```
      /usr/libexec/java_home -V
      ```
      
    - If 7 or 8 are not present somewhere in the list, install them, e.g:
   <http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>
    - Set 1.7 as default

      ```
      export JAVA_HOME=`/usr/libexec/java_home -v 1.7`
      ```

3. Ensure that Ant is installed on your local environment.
    <https://ant.apache.org/bindownload.cgi>

4. Ensure you have a copy of the hybris bundle from above (hybris5.3.tar.gz)

5. (optional) Use git to reset local hybris. You should never edit files in the local hybris, and always make changes to /fm modules. You can use git to track and reset those changes. Otherwise, if you did notice a problem, your best option would be to delete the hybris-install directory and do a fresh install.

    - Make sure you're in the base directory of your hybris install.

      ```
      cd ~/git/hybris-install
      ```

    - Initialize git

      ```
      git init
      ```
      
    - Make up a local branch to work off of, "develop" is fine. This has nothing to do with the fm-hybris code, it is purely for your local hybris-install.
      
      ```
      git checkout -b develop
      ```
      
    - Add all the files. Note this will be VERY slow due to the large file count.
      
      ```
      git add .
      ```
      
    - Commit the changes. (Again, may be slow)
      
      ```
      git commit -m "Inital commit"
      ```
      
    - From now on, use "git status" in the hybris-install directory to look for accidents.
    - Reset to stock with "git reset --hard HEAD", or you can also "git add && git stash" if some files won't go away. 
    - (Note that if you do this in the order provided, a reset will break the symlink you create in step 6. Remember to redo that symlink. You'll know if happened if the ant build complains about missing files.)

6. Link the hybris custom folder to the /fm modules we want to deploy
    
    - Make the custom directory to hold our modules
    
    ```
    mkdir ~/git/hybris-install/bin/custom
    ```
    
    - Link the fm directory
    
    ```
    ln -s ~/git/fm-hybris/hybris-deployment/fm ~/git/hybris-install/bin/custom
    ```
    
    - Verify the correct link was made

      ```
      cd ~/git/hybris-install/bin/custom && ls
      ```
      
    - Should see an "fm" folder, which contains all our modules
    - Same as ~/git/fm-hybris/hybris-deployment/fm

7. Build Hybris Code
    - In platform dir run setantenv

      ```
      cd ~/git/hybris-install/bin/platform/ && . ./setantenv.sh
      ```      
    - I've seen this not work on the first try, so if you see a complaint in this or the next step, try running it again
    - Do a clean build

      ```
      ant clean all
      ```

8. Run Hybris

      ```
     sh ./hybrisserver.sh
      ```

Import Project Into IDE - (If you used Setup With Vagrant, or to Troubleshoot Vagrant)
---
There's currently a bit of a gap with our setup. If you open the root folder of the git repository in IntelliJ, you will only see the files for our Vagrant box and FM's custom files. You will not hybris default files or base classes things are derived from.

- In IntelliJ, open the root folder of the project
    - File > Open > ~/git/fm-hybris
    - This shows all our vagrant setup files, and custom FM modules
- If you also need to see hybris files outside the FM project
    - Ensure you have the hybris bundle extracted somewhere:
    - E.g. cd ~/git/hybris-install
    - File > Open > (the root directory of the hybris extraction)
    - Open the hybris files in a New Window
    - Switch between windows with Command + ~ key on Mac

Import Project Into IDE - If you used Setup Without Vagrant
---
Thanks to symlinking the custom/fm/ folder, we can now access all the hybris files and the FM modules as a "correct" hybris project. NOTE that this way will NOT show you vagrant files, so for example it won't open Vagrantfile.

- Open the hybris-install directory to do your work
    - File > Open > ~/git/hybris-install
- If you need to edit vagrant files outside of hybris work
    - File > Open > ~/git/fm-hybris > New Window

Eclipse users: Note that if you import the Setup Without Vagrant in Eclipse or similar IDE, it will scan all the resulting ant build.xml files and offer you a huge array of imports. You probably only need "platform" and the "fm-" modules. Import others only as necessary, or import them all once and close projects you're not using.

Hybris User Setup
------------------
1. Navigate to the Hybris Management Console.

    <http://localhost:9001/hmc/hybris>
    
2. Log in (admin --> nimda).
3. Search for ID starting with "b2bb".
4. You should see a search result, "b2bb@fm.com". Double-click to edit it.
5. On the password tab (located in the middle of the screen), set "new password" and then "password (repeat)". Click "save" (again, located on the menu bar in the middle of the screen).

Frontend Build Tools
------------------
We're using Grunt to manage the compilation and minification of all JavaScript and CSS, and Bower to manage all vendor plugin dependencies. As we work on transitioning to this new workflow the progress is in a new *theme-fmmp* theme folder under `hybris-deployment/fm/fmstorefront/web/webroot/_ui/desktop/`

All build tools mentioned below do not need to run on any environment other than developers' local and only when modifying CSS or JS in the *theme-fmmp* theme directory. The final set of steps commits the compiled changes to the repository for the servers. DO NOT directly modify the compiled files under `hybris-deployment/fm/fmstorefront/web/webroot/_ui/desktop/theme-fmmp/dist/`.

### Update System to get fmmp theme
1. Navigate to https://localhost:9002/platform/update
2. Click `Update`

### To change the theme in your environment:

1. Navigate to the Management Console <https://localhost:9002/hmc/hybris>
2. Open *WCMS* in the sidebar and click on *Websites*. Open the federalmogul site by double-clicking it.
3. There should be an *Editor-Website* accordion tab, in the *Base Configuration* section there’s a dropdown for *Theme* with Green selected, change this to *FMMP* and click the *Save* button at the top of the page.

### Initial Setup (to use Bower and Grunt):

1. Bower and Grunt both require Node to be installed, find and install the LTS version appropriate for your environment at [this Downloads page](https://nodejs.org/en/download/).
2. In a terminal, `cd` to the `hybris-deployment` directory of this repository.
3. Run `node -v` to verify that node has been installed. You should get a response similar to `v6.9.1` where the version number is what you've installed in step 1 above.
4. Run `npm install`, this will create a `node_modules` directory in `hybris-deployment` and load all dependencies that are required automatically.
5. Run `sudo npm install -g grunt-cli` (Windows users should omit "sudo ", and may need to run the command-line with elevated privileges). This makes the `grunt` command work in your terminal for the Grunt steps in the *To run Grunt* section below.

### To run Bower (to load vendor plugin dependencies like jQuery and Bootstrap)

1. In a terminal, `cd` to the `hybris-deployment` directory of this repository.
2. Run `bower install` - this only needs to be done at first setup of the project, or if bower dependencies change.
3. The install command above will create a new `bower_components` directory under `hybris-deployment/fm/fmstorefront/web/webroot/_ui/desktop/` and load all the required packages and plugins (like jQuery and Bootstrap) automatically.

### To run Grunt (while making CSS and JavaScript changes)

1. In a terminal, `cd` to the `hybris-deployment` directory of this repository.
2. If this is your first time running Grunt, you need to download all its dependencies first by following the steps in the *Initial Setup* section above.
3. Run `grunt` and leave it open, it'll watch for CSS and JS file changes and compile them automatically. This grunt command is what automatically compiles the source files that are placed in the `/dist/` directory.
4. Again, DO NOT directly modify the compiled files under /dist/.  
5. Hit CTRL + C to stop the Grunt command.

### To check in CSS and JavaScript changes
1. When committing changed files to git, make sure you're checking in all changed files in `hybris-deployment/fm/fmstorefront/web/webroot/_ui/desktop/theme-fmmp/`, including the compiled files in the `/dist/` directory. 
2. Since the local environments and servers will use the compiled version of the files, it's imperative that you commit them to the repo as they’re updated.  

Usage
=======
Common Pages and Commands
------------------
- The Hybris Administrator Console (HAC) - <http://localhost:9001>
    - User: admin
    - Password: nimda
- The Hybris Management Console (HMC) - <http://localhost:9001/hmc/hybris>
    - User: admin
    - Password: nimda
- Local fmmotorparts.com - <http://localhost:9001/fmstorefront>
    - Login:
        - User: b2bb@fm.com
        - Password: Whatever you set in Step 5 from the Hybris User Setup section.
    - Part numbers to search on (after logged in):
        - CCH123
        - CCH1010
- To stop Hybris
    - From your Vagrant console, Control-C.
    
Build only modules you need
---
- You can build only specific modules at a time. This will lower build time from ~minutes to ~seconds. Example:
   - With Vagrant
      1. Inside the vagrant box

      ```
         cd /vagrant/fm/(module)
      ```

      ```
      ant clean all
      ```

    - Without Vagrant

      ```
      cd ~/git/fm-install/bin/platform/custom/fm/(module)
      ```

      ```
      ant clean all
      ```


- Also remember not to clean every time if you can help it
    - If you need to ensure all files are regenerated

      ```
      ant clean all
      ```

    - For simple code changes, much faster:

      ```
      ant all
      ```

- Note: Hybris automatically redeploys every time you do a build. Ensure you wait sufficient time for the deploy. Monitor the window where you're running hybris to ensure you see the "Server startup in XXX ms".

Managing Java Versions
---
Hybris requires JDK 7, and AEM requires JDK 8. Luckily, they'll stop complaining once their JVM is started, so basically all you have to do is switch over whenever you see a complaint. When in doubt, when working with hybris, ensure JDK 7 is selected.

- For Mac:
- Check Currently active Java version

      ```
      java -version
      ```

- Check all Java versions installed

      ```
      /usr/libexec/java_home -V
      ```

- Set default version (choose one)

      ```
      export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
      ```

      ```
      export JAVA_HOME=`/usr/libexec/java_home -v 1.7`
      ```

- Your ant builds must ALSO take place under 1.7, so make sure to do a "java -version" from the terminal you're running ant on. If you see warnings about "invalid bytecode 18", that means your terminal is set to 1.8.

(Optional) Give vagrant more resources
---
If you're running the vagrant box, it is helpful to donate as much memory as you can to Vagrant. Note that this really only applies to computers with 16+ gigs of RAM, and requires that you aren't also running all the virtual boxes for AEM integration.

- Edit your Vagrantfile (~/git/fm-hybris/Vagrantfile)
- Up the vb.memory and vb.cpus counts as much as you can
    - WARNING: Check available memory with Activity Monitor!
    - WARNING: Other running virtual boxes can reserve large chunks of memory
    - WARNING: Trying to start another box AFTER you've given your free memory to vagrant might freeze your system, and require a restart.