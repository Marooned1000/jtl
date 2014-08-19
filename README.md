JAVA Transformation Tool (JTT) Version 1.0 07/20/2014

GENERAL USAGE NOTES.
---------------------------------------

- JTT requires Java 6 or later to run.

- JTT is a software that automates the reduction process of transforming the CTLKC+ logic into the ARCTL logic and generates the extended NuSMV code from the input CTLKC+ model. To do so, the user has to input a CTLKC+ model and specifications then the JTT will generate the equivalent extended NuSMV code. This code will be used as an input for the extended NuSMV model checker to check whether the input model satisfies the specifications or not.

Running JTT
-------------------

 - Run Main.java class after copying all JTT files.
 - Insert the name of each model (Agent) and press # to end.
 - For each model, insert its arguments (names of agents interacting with the current agent).
 - Insert the states of the current model and press enter after each state and # to end.
 - Insert the name of the initial state of the current model.
 - For each state in the model: 
    1. Indicate whether it has a commitment or not. If yes, insert the name of the fulfillment state. 
    2. Insert its actions and press # to end. 
    3. For each action, insert who is performing the action. If the current agent performs the action, press enter; otherwise, insert the name of the agent              (argument). 
    4. Insert the target state of the current action.
 - Press # when you end inserting all the models.
 - Insert the CTLKC+ specifications. Specifications can be inserted using a text file or directly using the keyboard.
 - Finally, JTT will generate the extended NuSMV code for each model and the specifications.

----------------------------------------------------------------------------------------------------------------------------
JTT can be downloaded from
-----------------------------------------
Web site: https://github.com/Marooned202/jtl

For more information, you can contact:
-------------------------------------------------------------
E-mail: 
- bentahar (at) ciise.concordia.ca 
- faisalalsaqqar1 (at) gmail.com
- ehkasl (at) gmail.com

Copyright 2014 JTT. All rights reserved. JTT and its use are subject to copyright, trademark, patent and/or laws. 

