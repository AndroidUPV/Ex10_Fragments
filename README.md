# Ex10_Fragments
Lecture 02 - Development of Graphical User Interfaces (GUI)

The user can customize the Froyo of her dreams (size, topping, sauce) and place an order.
- There is a single Activity that holds a FragmentContainerView to display all the screens (implemented as Fragment).
- A single ViewModel is shared between the Fragments to access the state (size, topping, sauce) of the Froyo.
- As the navigation between Fragments is manually managed, the first Fragment (WelcomeFragment) registers a callback to properly handle the case when the Back Button is pressed.  