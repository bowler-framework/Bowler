SBT Sample Processor
====================

This is a blank SBT processor project. All it does it print the arguments to the console but it should help you get started with your processor.

To get started simple clone the repository: 
-------------------------------------------

<pre><code>git clone git://github.com/mads379/Sample-SBT-Processor.git
cd Sample-SBT-Processor	
sbt</code></pre>

Once SBT has started run the following commands in the SBT console

<pre><code>publish-local
*someName is com.sidewayscoding sample_sbt_processor 1.0
</code></pre>

Now you can run the processor as you would any other command

<pre><code>someName test test lolcats</code></pre>

For more information about processor check out the SBT wiki: http://code.google.com/p/simple-build-tool/wiki/Processors