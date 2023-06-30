import java.util.Map;

public class Sample {
  private static Map<Integer, Runnable> actions = 
    Map.of(
      1000, () -> System.out.println("action for 1000"),
      1001, () -> System.out.println("some action for 1001"),
      1002, () -> System.out.println("some specific action for 1002"),
      1003, () -> System.out.println("an action for 1003"),
      1004, () -> System.out.println("the action for 1004"),
      1005, () -> System.out.println("the action for code 1005")
    );
  //we can now provide a way for different parts of code to register
  //actions for different code. If we go to that extent, then we do not
  //have to change the hashmap in one place.

  //The code is DRY and also honor OCP.

  public static void action(int code) {
    actions.getOrDefault(code, () -> {}).run();    
  }

  /*
  public static void action(int code) {
    if(code == 1000) {
      System.out.println("action for 1000");
    } else {
      if(code == 1001) {
        System.out.println("some action for 1001");
      } else {
        if(code == 1002) {
          System.out.println("some specific action for 1002");
	} else {
          if(code == 1003) {
            System.out.println("an action for 1003");
	  } else {
            if(code == 1004) {
              System.out.println("the action for 1004");
	    } else {
              if(code == 1005) {
                System.out.println("the action for code 1005");
	      }
	    }
	  }
	}
      }
    }
  }
  */

  public static void main(String[] args) {
    action(1000);
    action(1001);
    action(1002);
    action(1003);
    action(1004);
    action(1005);
    action(1006);
  }
}

