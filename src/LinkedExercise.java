public class LinkedExercise {
  //The Exercise contained in this linked node, which cannot be replaced after this node is created
  private Exercise exercise;
  //The next linked node in this list
  private LinkedExercise next;
  //Creates a new node containing the provided exercise data with no following node
  LinkedExercise(Exercise data){
    exercise = data;
  }
  //Creates a new node containing the provided exercise data and next node
  public LinkedExercise(Exercise data, LinkedExercise next) {
    this.exercise = data;
    this.next = next;
  }

  /**
   * Accesses the next linked node in the list, which may be null
   * @return the reference to the node which follows this one in the list
   */
  public LinkedExercise getNext() {
    return this.next;
  }

  /**
   * Changes the node which follows this one to be the provided value, which may be null
   * Parameters:
   * @param node the reference to set as the next node in the list
   */
  public void setNext(LinkedExercise node) {
    this.next = node;
  }

  /**
   * Accesses the exercise stored in this linked node
   * @return the Exercise stored in this linked node
   */
  public Exercise getExercise() {
    return exercise;
  }

  /**
   * Returns a String representation of this linked exercise. This String will be:
   *  exercise.toString() + " -> "    // if next field is NOT null
   *  exercise.toString() + " -> END" // if next field is null
   * For instance,
   *      LinkedExercise ex1 = new LinkedExercise(new Exercise(WorkoutType.PRIMARY, "curl"));
   *      LinkedExercise ex2 = new LinkedExercise(new Exercise(WorkoutType.WARMUP, "stretch"), ex1);
   *      ex1.toString() returns "PRIMARY: curl (1) -> END"
   *      ex2.toString() returns "WARMUP: stretch (2) -> "
   * Overrides:
   * toString in class Object
   * @return a String representation of this linked exercise object
   */
  @Override
  public String toString() {
    if(next != null) {
      return exercise.toString() + " -> ";
    }
    return exercise.toString() + " -> END";
  }
 }

