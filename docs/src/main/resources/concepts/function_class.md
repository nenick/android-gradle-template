[Back to Index](../index.md)

# Function Class

I don't know if this style has a name.
I took this idea from some scala developer.

The base idea is to split code for hiding details and easy unit testing.

Whenever you have a more complex class function then create a own class for it with a single public method.
Additional convenience methods are also possible.


    public class TaskToDoFunction {
        public <return value> apply(<inout values>) {
        }
    }

---

[Back to Index](../index.md)