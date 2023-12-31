//////////////// WorkoutBuilder File Header //////////////////////////
//
// Title: Workout Builder
// Course: CS 300 Fall 2023
//
// Author: Liam Matthiasson
// Email: matthiasson@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: TA Office Hours
// Online Sources:
//
//////////////////////////////////////////////////////////////////////////////

import javax.tools.ForwardingJavaFileManager;
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
    head = null;
    tail = null;
    size = 0;
    warmupCount = 0;
    primaryCount = 0;
    cooldownCount = 0;
  }

  /**
   * Finds the index of a given exercise in this WorkoutBuilder list, if it is present.
   * @param findObject the exercise to search for in this list
   * @return the index of this object in the list if it is present; -1 if it is not
   */
  @Override
  public int indexOf(Object findObject) {
    Exercise find = (Exercise)findObject;
    LinkedExercise current = head;
    for(int i = 0; i < size; i++){
      if(current.getExercise().equals(findObject)){
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
  public Exercise get(int index) {
    if(index > size - 1){
      throw new IndexOutOfBoundsException("That index does not exist in this linked list");
    }
    LinkedExercise current = head;
    for(int i = 1; i <= index; i++){
      current = current.getNext();
    }
    return current.getExercise();
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
    Exercise added = (Exercise)newObject;
    WorkoutType type = added.getType();
    if(type == WorkoutType.WARMUP){
      addWarmup(added);
    } else if (type == WorkoutType.PRIMARY) {
      addPrimary(added);
    }
    else {
      addCooldown(added);
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
      primaryCount++;
      size++;
    }
    //if the warmup count is 0 that means we can make the head the warmup
    else if (warmupCount == 0) {
      head = new LinkedExercise(newExercise, head);
      primaryCount++;
      size++;
    }
    else if(primaryCount == 0 && cooldownCount == 0){
      tail.setNext(newPrimary);
      tail = newPrimary;
      primaryCount++;
      size++;
    }
    else {
      LinkedExercise current = head;
      while (current.getNext() != null) {
        if (current.getNext().getExercise().getType() == WorkoutType.PRIMARY) {
          newPrimary.setNext(current.getNext());
          current.setNext(newPrimary);
          primaryCount++;
          size++;
          return;
        } else if (current.getNext().getExercise().getType() == WorkoutType.COOLDOWN && primaryCount == 0) {
          newPrimary.setNext(current.getNext());
          current.setNext(newPrimary);
          primaryCount++;
          size++;
          return;
        }
        current = current.getNext();
      }
    }
  }

  /**
   * helper method that handles adding a new object to a linked list if it is of type cooldown
   * @param newExercise - object to be added
   */
  private void addCooldown(Exercise newExercise){
    //create new LinkedExercise
    LinkedExercise newCooldown = new LinkedExercise(newExercise, null);
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
   * Removes an exercise of the provided type from the list, if one exists, and decrements
   * the corresponding counter fields:
   * WARMUP: removes the FIRST (head) element from the list
   * PRIMARY: removes the FIRST exercise of type PRIMARY from the list
   * COOLDOWN: removes the LAST (tail) element from the list
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
  /**
   * helper method that removes an exercise of type warmup
   * @return Exercise that was removed
   */
  private Exercise removeWarmup(){
    Exercise returned = head.getExercise();
    LinkedExercise afterHead = head.getNext();
    head = null;
    head = afterHead;
    size--;
    warmupCount--;
    return returned;
  }
  /**
   * helper method that removes an exercise of type Primary
   * @return Exercise that was removed
   */
  private Exercise removePrimary(){
    if (warmupCount == 0) {
      Exercise removed = head.getExercise();
      head = head.getNext();
      size--;
      primaryCount--;
      return removed;
    }
    LinkedExercise current = head;
    for(int i = 0; i < size; i++){
      if(get(i).getType() == WorkoutType.PRIMARY){
        Exercise removed = get(i);
        findAt(i - 1).setNext(findAt(i).getNext());
        size--;
        primaryCount--;
        return removed;
      }
    }
    throw new NoSuchElementException("No primary in list, cannot remove");
  }
  /**
   * helper method that removes an exercise of type Cooldown
   * @return Exercise that was removed
   */
  private Exercise removeCooldown(){
    Exercise returned = tail.getExercise();
    tail = findAt(size -1);
    findAt(size-1).setNext(null);
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
    int index = -1;
    WorkoutType thisType = null;
    Exercise returned = null;
    for(int i = 0; i < size; i ++){
      if(get(i).getExerciseID() == exerciseID){
        index = i;
        thisType = get(i).getType();
        returned = get(i);
        break;
      }
    }
    if(index == -1){
      throw new NoSuchElementException("No exercises match that provided exerciseID number");
    }
    if(size == 1){
      head = null;
      tail = null;
      size--;
    } else if (index == 0) {
      head = head.getNext();
    }
    else{
      findAt(index - 1).setNext(findAt(index).getNext());
    }
    if(thisType.equals(WorkoutType.WARMUP)){
      warmupCount--;
    } else if (thisType.equals(WorkoutType.PRIMARY)) {
      primaryCount--;
    }
    else{
      cooldownCount--;
    }
    size--;
    return returned;
  }

  /**
   * helper method that returns the linked list located at a certain index
   * parses through the list until it has reached  the element at the element and subsequently returns
   * @param index - the index which the desired element is located
   * @return LinkedExercise at the index specified by parameter
   */
  private LinkedExercise findAt(int index) {
    if (index == 0) {
      return head;
    }
    LinkedExercise current = head;
    for (int i = 0; i < index; i++) {
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
