public class Pet implements Comparable {
 int petId;
 String petType;
 public Pet(int argPetId, String argPetType) {
  petId = argPetId;
  this.petType = argPetType;
 }
 public int compareTo(Object o) {
  Pet petAnother = (Pet)o;
  //natural alphabetical ordering by type
  //if equal returns 0, if greater returns +ve int,
  //if less returns -ve int
  return this.petType.compareTo(petAnother.petType);
 }
 public static void main(String[] args) {
  List list = new ArrayList();
  list.add(new Pet(2, "Dog"));
  list.add(new Pet(1, "Parrot"));
  list.add(new Pet(2, "Cat"));
  Collections.sort(list); // sorts using compareTo method
  for (Iterator iter = list.iterator(); iter.hasNext();) {
   Pet element = (Pet) iter.next();
   System.out.println(element);
  }
  //Output: Cat, Dog, Parrot
 }
 public String toString() {
  return petType;
 }
}

public class PetComparator implements Comparator, Serializable{
 public int compare(Object o1, Object o2) {
  int result = 0;
  Pet pet = (Pet)o1;
  Pet petAnother = (Pet)o2;
  //use Integer class's natural ordering
  Integer pId = new Integer(pet.getPetId());
  Integer pAnotherId = new Integer(petAnother.getPetId());
  result = pId.compareTo(pAnotherId);
  //if ids are same compare by petType
  if(result == 0) {
   result= pet.getPetType().compareTo(petAnother.getPetType());
  }
  return result;
 }
 public static void main(String[] args) {
  List list = new ArrayList();
  list.add(new Pet(2, "Dog"));
  list.add(new Pet(1, "Parrot"));
  list.add(new Pet(2, "Cat"));
  Collections.sort(list, new PetComparator());
  for (Iterator iter = list.iterator(); iter.hasNext();){
   Pet element = (Pet) iter.next();
   System.out.println(element);
  }
  //Output: Parrot, Cat, Dog.
 }
}

With Buffering: yields better performance
try{
 File f = new File("myFile.txt");
 FileInputStream fis = new FileInputStream(f);
 BufferedInputStream bis = new BufferedInputStream(fis);
 int count = 0;
 int b = 0 ;
 while((b = bis.read()) != -1){
  if(b== '\n') {
   count++;
  }
 }
 //bis should be closed in a finally block.
 bis.close() ;
}
catch(IOException io){}
Note: bis.read() takes the next byte from the input buffer and only
rarely access the underlying operating system.
