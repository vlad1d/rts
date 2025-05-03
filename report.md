   # Report

Vlad Ichim (s5096928) & Milan Boomsma (s4810945)

## Introduction

The program allows the user to create a world in which they can create bases, connections between them, as well as armies.
Here, strategic battles are the key to having the team that dominates the board.  The main purpose of the game is to allow 
the user to choose between fighters for their bases, form armies, send them to battle and conquer enemy bases. The game 
also provides the user with the choice to add various events that can influence the outcome of said battles, such as gaining 
strength stations, teleporters and natural disasters. In order to not lose the progress, the user can choose to save the game.
 
> *Very briefly describe what your program does.


>Expected length: ~100 words

## Program design

Design wise, the program follows the architectural pattern MVC. This offers better organisation and extensibility of the code,
rather than if they were all in the same place.

Model contains all logic of the program that is necessary for representation on screen. In this program, it is represented
by Graph, Node, Edge, Army, etc. Model does not interact with neither the view nor the controller. This is an essential choice,
as making changes to the model shouldn't have effects on the others, for easy changeability. Another essential choice regarding
the model was to not include in the node information regarding the width and height, as these all have to do with how the nodes
are shown to the user rather than a property of them.

View contains everything that is shown on the screen. This includes a variety of things, such as pop-up messages, buttons,
file choosers and option panels. This makes changes on the screen whenever something happens. The screens are only to be
called from the controller in various contexts. These contexts depend on what the screens are created to show. For example,
SaveScreen represents a file chooser that is called from the controller when the Save button is pressed. The buttons in
"view" all have actions to controller classes, where the action that happens after pressing the button is being handled.
This is because what is happening behind the screen has to be handled in the controller, rather than in the view. The view
also creates everything that is displayed on the screen. The panel is constantly updated, methods in this folder (such as
GraphPanel) being responsible for drawing nodes, edges, armies, etc.

Lastly, controller is the main section of the game. This handles all activity behind the screen, acting as an intermediary
between the model and view, handling inputs and coordinating interactions. In controller, we handle actions that happen when
you press a button (such as addNode, simulation, etc). Here, things like moving the armies or initiating battles happen.
Moreover, after all these changes, the view is notified to update the graphical representation of the game. By implementing
an interface in the controller we are able to call and update the graph, while maintaining the connection between these two.

A design implementation that might be perceived as different that we applied is selection of a node keeps it selected until
it is clicked again. In many applications it is highly annoying to create bases that require the repetition of some steps.
By keeping a node/edge selected until it is selected again, we allow the user to efficiently and quickly create connections
to multiple other bases, rather than just one and having to repeat the process. Since the game has many options that can
be done on the same node/edge (such as adding edges, an army, an event), this was the design that we agreed upon.

Another design implementation choice was making the Faction and Teams of type "enum", rather than class. This was due to
the fact that we were given a fixed set of Factions and Teams. This approach simplified our code and made it more clear,
while also preventing unintended values from being used. 

Finally, the design of our program follows the MVC architecture, which promotes maintainability. This allows easy 
testing, as well as modification for each part separately. The design decisions made improve code clarity and ensure
that the functionality of the program aligns with the requirements of the assignment.

> *Here you go over the structure of the program. Try not to go too in-depth here implementation-wise, but rather discuss the important components and relations between them. 
> If you think it can help, feel free to add a simple diagram here. The design of the program should be clear to the reader. 
> 
> In particular, describe the model of the program. How is it structured? How did you make sure to separate the different aspects of the program?
> How do the `model`, `view` and `controller` interact with each other?
> Additionally, you should include some design decisions in here. There is no need to provide an explanation for every single thing, 
> but there are often multiple ways of implementing a feature and in those cases it makes sense to state why you chose one over the other.*

> Expected length: as much as you need to explain the above.

## Evaluation of the program

The program is tested against errors, these being prevented through different functionalities that I will go more in depth
for each case. The main functionalities, such as creating bases, connecting them and forming armies are created 
to run smoothly and as intended. Errors on this part are prevented by disabling the buttons which allows their creation
and removal when such action is not possible. In the case of the events, besides disabling the buttons when necessary,
checks are being run for each selection to make sure that the same event is not applied twice to the same node/edge. In
the case of removal of one event, the user is only prompted with the option to remove the existent events, rather than
all of them. All dialogues with the user return null when not successful, thus preventing an unexpected error. Finally,
in battle simulations, not only do nodes and edges have a list of armies that are present in them, but armies also possess 
information about what node/edge they are located on to prevent null calls of nodes/edges in battles. These simulations 
also run checks before and after movement for all nodes and edges to ensure that no two teams are positioned on the same
node, which can be caused by the unexpected placement of the teleporter event.

Since no software is bug-free, it is crucial for projects like these to be constantly checked for unexpected errors that 
can be caused by edge cases and unexpected user inputs. 

If time allowed, the game could become much more interesting by adding more events, as well as "stations" which are 
pre-set bases which players can connect to their bases and send their armies to train, rest (gain health) or gain super-powers.
This would add complexity to the game and would promote the idea of creating a connected "community", like a village for
the base rather than just a map, which can be expanded further. Of course, to create links between nodes the player would
require a certain amount of coins, which can be gained from battling. This would create a stronger meaning for the game,
as it connects its two main purposes: creating bases and battling. Finally, the game can be brushed up by improving the 
user interface. Complex graphics, interesting and refined button animations, as well as interactive visual feedback can 
make the game more interactive and exciting for the players.

> *Discuss the stability of your implementation. What works well? Are there any bugs? Is everything tested properly? Are there still features that have not been implemented? Also, if you had the time, what improvements would you make to your implementation? Are there things which you would have done completely differently?*

>Expected length: ~300-500 words

## Questions

Please answer the following questions:

1. In this assignment, the program should follow the Model View Controller (MVC) pattern. Please explain the design of the program in terms of the MVC pattern. Specifically try to answer the following questions:
   - MVC consists of three components: Model, view and controller. Can you please explain the role of each component? Please provide examples of these roles from the assignment. How are these three roles (i.e. Model, view and controller) are implemented in the assignment?
   - MVC enforces special constraints on the dependencies between its three components: Model, view and controller. Please explain these constraints, and why are they important?

___

Answer: MVC consists of three components, namely: model, view and controller. The model is the basic application,
it contains all the data and logic with regard to what the program should actually do. The model is an example of an event driven program, the model will not do anything, unless the user asks it do something.
The view is the visual representation of the model, whenever the model changes, these changes can be seen on the screen of the user since the view will be updated.
This is taken care of by the view.
The controller is responsible for taking care of user input. When the controller receives input from a user, for example, the user click on the "Add Node" button in our program,
the controller will tell the model to change, the model will create a node and add this to the graph, the view will then be updated and the user can now see a new node on the screen.
To summarize, the user uses the controller and sees the view, the controller manipulates the model and the model updates the view.
There are also special constraints on dependencies between the three components in MVC. The model should be created individually, without any knowledge of how it will be displayed or manipulated, this way the model won't be dependent on the controller or the view. For example, if you want to change your program to a text-based program from a visual program, you will only have to change your view without touching the model.
The view should thus be read-only, it should not be able to modify any data in the model.
The controller should not have any dependencies on the model or view, it just receives input from the user via the view and accordingly updates the model.

___

2. The Swing library provides the ability to create nested user interface components. In this assignment, you created multiple JPanel components on the user interface. These contain other user interface components to build-up a tree of user interface components.
Which design pattern does Swing implement to create a hierarchy of user interface components? Please explain this pattern and how it is implemented in Swing.

___

Answer: The design pattern used to create a hierarchy of user interface components
is the composite design pattern. This design pattern treats the components of the
composite object and the composite object itself uniformly by defining a common user-interface,
this allows users to interact with the composite object and its components in a reliable way. An example of this design pattern
is the JPanel in Swing, this component can hold many other components such as JLabels and JButtons. It can also hold another JPanel,
this JPanel can then also hold components, this way you get a tree-like structure of user interface components.

___

3. The Observer pattern is useful to implement the MVC pattern. Can you please explain the relationship between the Observer pattern and the MVC pattern?
Please provide an example from the assignment on how the Observer pattern supports implementing the MVC pattern.

___

Answer: The observer pattern is a behavioral design pattern, it lets objects communicate with eachother.
You have an observable and an observer, the observable notifies the observer whenever it state is changed.
The observer will act accordingly, in our case it will update the view. In our program we have an observable,
the graph that contains the nodes and edges, if anything is changes to these nodes or edges, the view will be
notified and it will update accordingly. For example: when a node is clicked, the view will be notified.
The view will then highlight the selected node and the buttons that are only available when a node is clicked
will be enabled.

___

## Process evaluation

The process that led to the final code and report was a collaborative effort on one of the first substantive projects.
Some aspects of the implementation, such as creating the graph, were straightforward and did not require much effort. 
One of the first encountered issues was the implementation of the Model-View-Controller which I found vague and 
confusing. However, through team-work I was able to understand both the logic behind it and its importance. My 
mistake of not implementing this at first served as a learning experience which taught me how much easier it is to 
understand and create code that is well-structured. Other difficulties were caused by unexpected errors in the program which
were prevented by continuously testing new functionalities. It is crucial to constantly check new changes in your code 
so that you can quickly identify the issues. Finally, another important lesson we learned from this assignment is the
importance of communication and collaboration within a team. By talking to each-other we identified the core strengths
that each individual has and how we can use them to work together on the project.

> *Describe shortly the process that led to the final code and the report. What was easy, what was difficult? Did you make interesting mistakes? What have you learned from this assignment?*

> Expected length: ~150 words

## Conclusions

In conclusion, creating this game provided a unique opportunity to apply newly learned Java functionalities and programming
concepts. The implementation covers the core features mentioned in the assignment, as well as additional enhancements 
that elevate the gaming experience. This game offered us a chance to expand our Java programming skills, while providing a
fun and thrilling experience for the players. While the game has the desired functionality, it is crucial to continue testing
and fixing bugs to ensure the stability of the game and address any unforseen issues.

> *Add a very short summary/concluding remarks here*
