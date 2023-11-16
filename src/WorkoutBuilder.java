import java.util.List;
import java.util.NoSuchElementException;

public class WorkoutBuilder implements ListADT{
  //the number of exercises with WorkoutType equal to COOLDOWN in this WorkoutBuilder list
  private int cooldownCount;
  //The node containing the element at index 0 of this singly-linked list
  private LinkedExercise head;
  //The number of exercises with WorkoutType equal to PRIMARY in this WorkoutBuilder list
  private int primaryCount;
  //The total number of exercises contained in this WorkoutBuilder list
  private int size;
  //The node containing the last element of this singly-linked list
  private LinkedExercise tail;
  //The number of exercises with WorkoutType equal to WARMUP in this WorkoutBuilder list
  private int warmupCount;

  /**
   * Accesses the total number of elements in this WorkoutBuilder list
   * Specified by:
   * size in interface ListADT<Exercise>
   * @return the size of this list
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Accesses the number of warm-up exercises stored in this WorkoutBuilder list
   * @return the count of exercises with WorkoutType equal to WARMUP
   */
  public int getWarmupCount(){
    return warmupCount;
  }

  /**
   * Accesses the number of primary exercises stored in this WorkoutBuilder list
   * @return the count of exercises with WorkoutType equal to PRIMARY
   */
  public int getPrimaryCount() {
    return primaryCount;
  }

  /**
   * Accesses the number of cool-down exercises stored in this WorkoutBuilder list
   * @return the count of exercises with WorkoutType equal to COOLDOWN
   */
  public int getCooldownCount(){
    return cooldownCount;
  }

  /**
   * Specified by:
   * isEmpty in interface ListADT<Exercise>
   * @return true if this list contains no elements and neither its head nor tail refer to LinkedExercise objects
   */
  @Override
  public boolean isEmpty() {
    return head == null;
  }

  /**
   * Removes all elements from this list. The list will be empty after this call returns.
   */
  public void clear() {
    //if the head is null that must mean the list is empty
    if(isEmpty()){
      System.out.println("List is already empty");
      return;
    }
    //starts after the head and then we manually clear head?
    LinkedExercise current = head.getNext();
    head = null;
    //goes through the list (as long as its not null it continues)
    while (current != null) {
      LinkedExercise next = current.getNext();
      current.setNext(null);
      current = next;
    }
  }
  @Override
  public int indexOf(Object findObject) {
    LinkedExercise current = head;
    for(int i = 0; i < size; i++){
      if(current.equals(findObject)){
        return i;
      }
      else{
        current = current.getNext();
      }
    }
    return -1;
  }

  /**
   * Returns the exercise stored at the given index of this list without removing it.
   * Specified by:
   * get in interface ListADT<Exercise>
   * IndexOutOfBoundsException - with a descriptive error message if the index is not valid for the current size of this list
   * @param index position within this list
   * @return the exercise stored at the given index of this list
   */
  @Override
  public Object get(int index) {
    if(index > size - 1){
      throw new IndexOutOfBoundsException("That index does not exist in this linked list");
    }
    LinkedExercise current = head;
    for(int i = 1; i <= index; i++){
      current = current.getNext();
    }
    return current;
  }

  /**
   * Adds the provided Exercise to the appropriate position in the list for its WorkoutType, and increments the corresponding counter fields:
   * WARMUP: adds to the FRONT (head) of the list
   * PRIMARY: adds after all warm-ups and before any cool-downs; if there are any existing primary exercises, adds before all of those as well
   * COOLDOWN: adds to the END (tail) of the list
   * We recommend implementing private helper methods for each of these cases, but this is not required. Remember to consider the case where you are adding the very first exercise to the list!
   * Specified by:
   * add in interface ListADT<Exercise>
   * @param newObject the exercise to add to the WorkoutBuilder list
   */
  @Override
  public void add(Object newObject) {
    Exercise newExercise = (Exercise) newObject;
    WorkoutType type = newExercise.getType();
    if(type == WorkoutType.WARMUP){
      addWarmup(newExercise);
    } else if (type == WorkoutType.PRIMARY) {
      addPrimary(newExercise);
    }
    else {
      addCooldown(newExercise);
    }
  }
  /**
   * helper method that handles adding a new object to a linked list if it of type warmup
   * @param newExercise - object to be added
   */
  private void addWarmup(Exercise newExercise){
    //if the list is empty then both the head and tail should be set to the new object
    if(isEmpty()){
      head = new LinkedExercise(newExercise);
      tail = head;
    }
    else{
      LinkedExercise newHead = new LinkedExercise((Exercise) newExercise, head);
      head = newHead;
    }
    warmupCount++;
    size++;
  }

  /**
   * helper method that handles adding a new object to a linked list if it is of type primary
   * @param newExercise - object to be added
   */
  private void addPrimary(Exercise newExercise){
    //create a new linkedExercise first
    LinkedExercise newPrimary = new LinkedExercise(newExercise);
    //if the list is empty then both the head and tail should be set to the new object
    if(isEmpty()){
      head = newPrimary;
      tail = newPrimary;
    }
    else if (warmupCount == 0) {
      head = new LinkedExercise(newExercise, head);
    }
    else {
      int index = warmupCount - 1;
      if (index == size - 1) {
        tail.setNext(newPrimary);
        tail = newPrimary;
      } else {
        newPrimary.setNext(findAt(index).getNext());
        findAt(index - 1).setNext(newPrimary);
        if (index == size - 1) {
          tail = newPrimary;
        }
      }
    }
    primaryCount++;
    size++;
  }

  /**
   * helper method that handles adding a new object to a linked list if it is of type cooldown
   * @param newExercise - object to be added
   */
  private void addCooldown(Exercise newExercise){
    //create new LinkedExercise
    LinkedExercise newCooldown = new LinkedExercise((Exercise) newExercise, null);
    //if the list is empty then both the head and tail should be set to the new object
    if(isEmpty()){
      head = newCooldown;
      tail = newCooldown;
    }
    else {
      tail.setNext(newCooldown);
      tail = newCooldown;
    }
    cooldownCount++;
    size++;
  }
  /**
   * Removes an exercise of the provided type from the list, if one exists, and decrements the corresponding counter fields:
   * WARMUP: removes the FIRST (head) element from the list
   * PRIMARY: removes the FIRST exercise of type PRIMARY from the list
   * COOLDOWN: removes the LAST (tail) element from the list
   * You are encouraged to implement private helper methods for each of these cases as well, but this is not required. Be sure to check that there are any exercises with the given type present in the list, and remember to consider the case where you are removing the very last exercise from the entire list!
   * Parameters:
   * type - the type of exercise to remove from the list
   * Returns:
   * the exercise object that has been removed from the list
   * Throws:
   * NoSuchElementException - if there are no exercises in the list with the given WorkoutType
   */
  public Exercise removeExercise(WorkoutType type)
      throws NoSuchElementException{
    if(type == WorkoutType.WARMUP){
      if(warmupCount == 0){
        throw new NoSuchElementException("No workout of type warmup");
      }
      if(size == 1){
        Exercise exerciseRemoved = head.getExercise();
        head = null;
        tail = null;
        return exerciseRemoved;
      }
      else{
        return removeWarmup();
      }
    }
    else if(type == WorkoutType.PRIMARY){
      if(primaryCount == 0){
        throw new NoSuchElementException("No workout of type primary");
      }
      if(size == 1){
        Exercise exerciseRemoved = head.getExercise();
        head = null;
        tail = null;
        return exerciseRemoved;
      }
      else {
        return removePrimary();
      }
    }
    else{
      if(cooldownCount == 0){
        throw new NoSuchElementException("No workout of type cooldown");
      }
      if(size == 1){
        Exercise exerciseRemoved = head.getExercise();
        head = null;
        tail = null;
        return exerciseRemoved;
      }
      else {
        return removeCooldown();
      }
    }

  }
  private Exercise removeWarmup(){
    Exercise returned = head.getExercise();
    LinkedExercise afterHead = head.getNext();
    head = null;
    head = afterHead;
    size--;
    warmupCount--;
    return returned;
  }
  private Exercise removePrimary(){
    Exercise returned = findAt(warmupCount-1).getExercise();
    findAt(warmupCount - 1).setNext(findAt(warmupCount).getNext());
    size--;
    primaryCount--;
    return returned;
  }
  private Exercise removeCooldown(){
    Exercise returned = findAt(cooldownCount-1).getExercise();
    findAt(cooldownCount-2).setNext(null);
    cooldownCount--;
    size--;
    return returned;
  }

  /**
   * Removes the exercise with the provided ID number from the list, if it is present,
   * and adjusts any corresponding counter fields as necessary. This method uses a linear search algorithm.
   * @param exerciseID  the unique identifier of the exercise to be removed
   * @return the exercise object that has been removed from the list
   * @throws NoSuchElementException - if no exercises in the list match the provided exerciseID number
   */
  public Exercise removeExercise(int exerciseID)
      throws NoSuchElementException{
    LinkedExercise current = head;
    WorkoutType thisType = current.getExercise().getType();
    int index = 0;
    for(int i = 0; i < size; i++){
      if(current.getExercise().getExerciseID() == exerciseID){
        index = i;
        break;
      }
      current = current.getNext();
    }
    if(current == null){
      throw new NoSuchElementException("No exercises math that provided exerciseID number");
    }
    if(size == 1){
      head = null;
      tail = null;
      return current.getExercise();
    }
    findAt(index - 1).setNext(current.getNext());
    if(thisType.equals(WorkoutType.WARMUP)){
      warmupCount--;
    } else if (thisType.equals(WorkoutType.PRIMARY)) {
      primaryCount--;
    }
    else{
      cooldownCount--;
    }
    size--;
    return current.getExercise();
  }

  /**
   * helper method that returns the linked list located at a certain index
   * parses through the list until it has reached the element at the element and subsequently returns
   * @param index - the index which the desired element is located
   * @return LinkedExercise at the index specified by parameter
   */
  private LinkedExercise findAt(int index) {
    if (index == 0) {
      return head;
    }
    LinkedExercise current = head;
    for (int i = 0; i < index - 1; i++) {
      if (current == null || current.getNext() == null) {
        return null;
      }
      current = current.getNext();
    }
    return current;
  }

  /**
   * Returns a String representation of the contents of this list, as the concatenated String
   * representations of all LinkedExercise nodes in this list.
   * @return return a String representation of the content of this list. If this list is empty, an empty String ("") will be returned.
   */
  @Override
  public String toString() {
    String returnStatement = "";
    if (isEmpty()) {
      return returnStatement;
    } else {
      LinkedExercise current = head;
      while(current != null) {
        returnStatement = returnStatement + current.toString();
        current = current.getNext();
      }
    }
    return returnStatement;
  }
}
