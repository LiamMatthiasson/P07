import java.util.LinkedList;
import java.util.NoSuchElementException;

public class WorkoutBuilderTester {
  
  // checks for the correctness of the WorkoutBuilder.clear() method
 public static boolean testClear(){
   Exercise.resetIDNumbers();
   WorkoutBuilder b = new WorkoutBuilder();
   b.clear();
   //already empty see if anything weird happens
   boolean case1 = false;
   if(b.getCooldownCount() == 0 && b.getPrimaryCount() == 0 && b.getWarmupCount() == 0
   && b.isEmpty()){
     case1 = true;
   }
   Exercise one = new Exercise(WorkoutType.PRIMARY, "Bench Press");
   Exercise two = new Exercise(WorkoutType.COOLDOWN, "Child's Pose");
   Exercise three = new Exercise(WorkoutType.WARMUP, "Jog");
   Exercise four = new Exercise(WorkoutType.PRIMARY,"Squat");
   //now add to b
   b.add(one);
   b.add(two);
   b.add(three);
   b.add(four);
   //now clear
   b.clear();
   boolean case2 = false;
   if(b.getCooldownCount() == 0 && b.getPrimaryCount() == 0 && b.getWarmupCount() == 0
       && b.isEmpty()){
     case2 = true;
   }
   return case1&&case2;
 }

 // checks for the correctness of the WorkoutBuilder.add() method
 public static boolean testAddExercises() {
   Exercise.resetIDNumbers();
   Exercise one = new Exercise(WorkoutType.PRIMARY, "Bench Press");
   Exercise two = new Exercise(WorkoutType.COOLDOWN, "Child's Pose");
   Exercise three = new Exercise(WorkoutType.WARMUP, "Jog");
   Exercise four = new Exercise(WorkoutType.PRIMARY,"Squat");
   WorkoutBuilder a = new WorkoutBuilder();
   a.add(one);
   //check if no warmup no cooldown if added to front
   boolean case1 = a.get(0).getExerciseID() == one.getExerciseID();
   a.add(two);
   //check if it was added at back
   boolean case2 = a.get(1).getExerciseID() == two.getExerciseID();
   a.add(three);
   //check if it was added at front
   boolean case3 = a.get(0).getExerciseID() == three.getExerciseID();
   //check if it was added inbetween the two exercises
   a.add(four);
   boolean case4 = a.get(1).getExerciseID() == four.getExerciseID();
   return case1&&case2&&case3&&case4;
 }

 // checks for the correctness of BOTH of the WorkoutBuilder.removeExercise() methods
 public static boolean testRemoveExercises() {
   //our actual one
   WorkoutBuilder actual = new WorkoutBuilder();
   Exercise exercisePrimary = new Exercise(WorkoutType.PRIMARY, "Bench Press");
   Exercise exerciseCooldown = new Exercise(WorkoutType.COOLDOWN, "Child's Pose");
   Exercise exerciseWarmup = new Exercise(WorkoutType.WARMUP, "Jog");
   actual.add(exercisePrimary);
   actual.add(exerciseWarmup);
   actual.add(exerciseCooldown);
   //what we expect the outcome to be
   WorkoutBuilder expectedForRemoveWarmup = new WorkoutBuilder();
   expectedForRemoveWarmup.add(exerciseCooldown);
   expectedForRemoveWarmup.add(exercisePrimary);
   //remove from the first one
   actual.removeExercise(WorkoutType.WARMUP);
   boolean case1 = actual.equals(expectedForRemoveWarmup);
   //re-add back the warmup
   actual.add(exerciseWarmup);
   WorkoutBuilder expectedForRemovePrimary = new WorkoutBuilder();
   expectedForRemovePrimary.add(exerciseWarmup);
   expectedForRemovePrimary.add(exerciseCooldown);
   //remove from the array
   actual.removeExercise(WorkoutType.PRIMARY);
   boolean case2 = actual.equals(expectedForRemovePrimary);
   //add it back in
   actual.add(exercisePrimary);
   WorkoutBuilder expectedForRemoveCooldown = new WorkoutBuilder();
   expectedForRemoveCooldown.add(exercisePrimary);
   expectedForRemoveCooldown.add(exerciseWarmup);
   //check comparison
   boolean case3 = actual.equals(expectedForRemoveCooldown);
   Exercise.resetIDNumbers();
   //id numbers of 1 2 3 and 4
   Exercise one = new Exercise(WorkoutType.PRIMARY, "Bench Press");
   Exercise two = new Exercise(WorkoutType.COOLDOWN, "Child's Pose");
   Exercise three = new Exercise(WorkoutType.WARMUP, "Jog");
   Exercise four = new Exercise(WorkoutType.PRIMARY,"Squat");
   WorkoutBuilder actualIDRemove = new WorkoutBuilder();
   actualIDRemove.add(one);
   actualIDRemove.add(two);
   actualIDRemove.add(three);
   actualIDRemove.add(four);
   // so lets remove the one with ID number 3
   actualIDRemove.removeExercise(3); //(meaning it should get rid of "jog" )
   WorkoutBuilder expected = new WorkoutBuilder();
   expected.add(one);
   expected.add(two);
   expected.add(four);
   boolean case4 = actualIDRemove.equals(expected);
   return case1&&case2&&case3&&case4;
 }

 // checks for the correctness of the WorkoutBuilder.get() method
 public static boolean testGetExercises() {
   Exercise one = new Exercise(WorkoutType.PRIMARY, "Bench Press");
   Exercise two = new Exercise(WorkoutType.COOLDOWN, "Child's Pose");
   Exercise three = new Exercise(WorkoutType.WARMUP, "Jog");
   Exercise four = new Exercise(WorkoutType.PRIMARY, "Squat");
   WorkoutBuilder a = new WorkoutBuilder();
   a.add(one);
   a.add(two);
   a.add(three);
   a.add(four);
   boolean case1 = false;
   try {
     a.get(9);
   } catch (IndexOutOfBoundsException e) {
     case1 = true;
   }
   boolean case2 = a.get(0).equals(three);
   return case1 && case2;
 }

 // a test suite method to run all your test methods
 public static boolean runAllTests() {
   boolean clear = testClear(), 
       add = testAddExercises(), 
       remove = testRemoveExercises(),
       get = testGetExercises();
   
   System.out.println("test clear: "+(clear?"pass":"FAIL"));
   System.out.println("test add: "+(add?"pass":"FAIL"));
   System.out.println("test remove: "+(remove?"pass":"FAIL"));
   System.out.println("test get: "+(get?"pass":"FAIL"));
   
   // TODO: add calls to any other test methods you write
   
   return false; // TODO: replace this with the correct value
 }

 public static void main(String[] args) {
   runAllTests();
   demo();
 }

 /**
  * Helper method to display the size and the count of different boxes stored in a list of boxes
  * 
  * @param list a reference to an InventoryList object
  * @throws NullPointerException if list is null
  */
 private static void displaySizeCounts(WorkoutBuilder list) {
   System.out.println("  Size: " + list.size() + ", warmupCount: " + list.getWarmupCount()
       + ", primaryCount: " + list.getPrimaryCount() + ", cooldownCount: " + list.getCooldownCount());
 }

 /**
  * Demo method showing how to use the implemented classes in P07 Inventory Storage System
  * 
  * @param args input arguments
  */
 public static void demo() {
   // Create a new empty WorkoutBuilder object
   Exercise.resetIDNumbers();
   WorkoutBuilder list = new WorkoutBuilder();
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   // Add a primary exercise to an empty list
   list.add(new Exercise(WorkoutType.PRIMARY, "5k run")); // adds PRIMARY: 5k run (1)
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.WARMUP, "stretch")); // adds WARMUP: stretch (2) at the head of the list
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.PRIMARY, "bench press")); // adds PRIMARY: bench press (3)
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.WARMUP, "upright row")); // adds WARMUP: upright row (4) at the head of the list
   System.out.println(list); // prints list's content
   list.add(new Exercise(WorkoutType.WARMUP, "db bench")); // adds WARMUP: db bench (5) at the head of the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   // Add more exercises to list and display its contents
   list.add(new Exercise(WorkoutType.COOLDOWN, "stretch")); // adds COOLDOWN: stretch (6) at the end of the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.COOLDOWN, "sit-ups")); // adds COOLDOWN: sit-ups (7) at the end of the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: sit-ups (7) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.PRIMARY, "deadlift")); // adds PRIMARY: deadlift (8)
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: stretch (6) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.WARMUP); // removes WARMUP: db bench (5)
   System.out.println(list); // prints list's content
   list.removeExercise(3); // removes PRIMARY: bench press (3) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   try {
     list.removeExercise(25); // tries to remove box #25
   } catch (NoSuchElementException e) {
     System.out.println(e.getMessage());
   }
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   // remove all warm-ups
   while (list.getWarmupCount() != 0) {
     list.removeExercise(WorkoutType.WARMUP);
   }
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(1); // removes PRIMARY: 5k run (1) from the list -> empty list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.COOLDOWN, "walk")); // adds COOLDOWN: walk (9) to the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(8); // removes PRIMARY: deadlift (8) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(WorkoutType.COOLDOWN); // removes COOLDOWN: walk (9) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.add(new Exercise(WorkoutType.WARMUP, "pull-up")); // adds WARMUP: pull-up (10) to the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
   list.removeExercise(10); // removes WARMUP: pull-up (10) from the list
   System.out.println(list); // prints list's content
   displaySizeCounts(list); // displays list's size and counts
 }

}
