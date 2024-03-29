// These are the ONLY import statements you should use.
import java.util.ArrayList;
import java.util.Iterator;


/******************************************************************

Step 1.
Familiarize yourself with the Iterable<> interface in Java.  It simply requires that
a class that implements this interface must provide the following method:
  Iterator<T> iterator()
If a class C implements Iterable<T>, then you can write an interation as:
   for (T e: c) ...
where c is an object of class C.   
Important: In every place that you use a loop in your program, you should use this
syntax. The only exception is the test() method I give you below.

*******************************************************************/

// NO CODE HERE.

/*************************************************************
Step 2. 
Implement the generatic interface LessThanOrEqualTo<>, parameterized by a type T, which 
requires any class implementing this interface to define two methods:

     lessThan  - must take a parameter of type T and return a boolean.

     equalTo  - must take a parameter of type T and return a boolean


************************************************************/


interface LessThanOrEqualTo<T>{
  public Boolean lessThan(T t);
  public Boolean equalTo(T t);
}



/*************************************************************

Step 3.
Define an generic interface SetOf<>, which will specify the required methods for 
classes that behave like sets (Note: this has nothing to do with the built-in 
Java Set<> interface).

The SetOf<> interface has the following properties:

  - SetOf is parameterized by a type T that has to satisfy the constraints of the LessThanOrEqualTo<>
    interface, so that two objects of type T can be compared using lessThan() and equalTo().

  - Any class C implementing SetOf<T>, for some T meeting the above constraints, must also
    implement the Iterable<> and LessThanOrEqualTo<> interfaces, so that:
      -- You can iterate over the elements of C using the iteration syntax discussed 
         in step 1.
      -- An object of class C can be compared to another object of a class implementing
         SetOf<T> (i.e. comparing two sets) using lessThan() and equalTo().

Any class C implementing SetOf<T> (i.e. a class representing a set of T's) must implement 
the following methods.

    get -- takes no parameters an returns an element of type T. The purpose of get() is
           to return an element of the set, so that one can write
                   T x = s.get();
           assuming s is an object of a class implementing SetOf<T>.      

    add -- takes parameter of type T and doesn't return a value. The purpose of add() is
           to add the parameter to the set represented by the "this" object, so that one
           can write
                  s.add(x);
           to insert x into the set represented by s.

    empty -- takes no parameter and returns a boolean. The purpose of empty() is to
             return true when the "this" object represents the empty set, so one can 
             write
                  if (s.empty()) ...

    member -- takes a parameter of type T and returns a boolean. The purpose of member()
              is to return true when the parameter already found in the set, so one can
              write
                  if (s.member(x)) ...

    subset -- takes as a parameter another set (i.e. an object of a class implementing 
              SetOf<T>). It should return true if the set represented by the "this" object
              is a subset of the set represented by the parameter, false otherwise. For 
              example,
                     s1.subset(s2)
              should evaluate to true if every element of s1 is also an element of s2.

    union -- takes as a parameter another set (i.e. an object of a class
             implementing SetOf<T>) and returns an object implementing SetOf<T>. The purpose
             of union() is to return a new set that results from taking the union of 
             the set represented by the "this" object and the set represented by the 
             parameter, allowing one to write:
                 SetOf<T> s = s1.union(s2)

  intersection -- takes as a parameter another set (i.e. an object of a class
             implementing SetOf<T>) and returns an object implementing SetOf<T>. The purpose
             of intersection() is to return a new set that results from taking the intersection of 
             the set represented by the "this" object and the set represented by the 
             parameter, allowing one to write:
                 SetOf<T> s = s1.intersection(s2)

  difference -- takes as a parameter another set (i.e. an object of a class
             implementing SetOf<>) and returns an object implementing SetOf<T>. The purpose
             of union() is to return a new set that results from taking the difference 
             between the set represented by the "this" object and the set represented by the 
             parameter, allowing one to write:
                 SetOf<T> s = s1.difference(s2)
             to compute the set of elements in s1 that are not in s2.

*************************************************************/


interface SetOf<T extends LessThanOrEqualTo<T>> extends LessThanOrEqualTo<SetOf<T>> , Iterable<T>{
  T get();
  void add(T t);
  boolean empty();
  boolean member(T t);
  boolean subset(SetOf<T> other);
  SetOf<T> union (SetOf<T> other);
  SetOf<T> intersection (SetOf<T> other);
  SetOf<T> difference (SetOf<T> other);
  
}

/*************************************************************

Step 4.  

Define a generic class ASet<> that is parameterized by an element type T
and implements the interface SetOf<T>.  Here are some important points to note
about ASet<T>.

  - if you like, you can use the built-in Java generic class ArrayList<> in 
    your definition of ASet<>.  However, ASet<> should not be derived from
    ArrayList<>.

  - In addition to the methods required by SetOf<T> that you have to implement, you also
    have to implement the methods required by Iterable<> and LessThanOrEqualTo<>.

  - You should override the toString() method in ASet<> so that the elements of the 
    set are be printed out.

  - The only method() that should actually modify the set represented by the "this"
    object is the add() method.  All the other methods should leave the "this" object
    and any object passed as a parameter unmodified. For example, in
       s = s1.union(s2);
    both s1 and s2 should remain unchanged and s should refer to a new set object.

  - Because objects of type ASet<T> represtent a set of T's, there should not be
    duplicates of any element in a set. That is, the add() method should not add
    an element to a set if the element is already in the set, as determined by the 
    equalTo() operator for type T.

  - The subset() method for comparing two SetOf<T>'s should also use the equalTo() method 
    for type T.

  - the lessThan() operator on sets should be defined so that "s1.lessThan(s2)" returns true
    iff s1 is a PROPER subset of s2 (i.e. there is at least one element in s2 not in s1).

  - the equalTo() operator on sets should be defined so that "s1.equalTo(s2)" returns true
    iff s1 is a subset of s2 and s2 is a subset of s1.

  - To make sure you are on the right track, here are the number of lines that my
    version of each method is:  

         constructor: 1 line;  get: 1 line; empty: 1 line; add: 3 lines; 
         member: 3 lines; subset: 7 lines; lessThan: 1 line; equalTo: 1 line;
         union: 8 lines; intersection: 7 lines; difference: 7 lines; iterator: 1 line;
         toString: 1 line (but you can implement a better toString if you want)

    Your's can be longer, but shouldn't be much longer.

************************************************************/

class ASet<T extends LessThanOrEqualTo<T>> implements SetOf<T>{
    public ArrayList<T> arrayList; 
    //Constructor
    public ASet(){
        this.arrayList = new ArrayList<T>();
    }

    @Override
    public T get() {return iterator().next();}

    @Override
    public boolean empty() {return arrayList.isEmpty();}
    
    @Override
    public void add(T t) { //use equalTo operator
        for(T e : this){
            if(t.equalTo(e))
                return;
        }
        arrayList.add(t);
    }    

    @Override
    public boolean member(T t) {
        for(T e : this){
            if (t.equalTo(e))
                return true;
        }
        return false;
    }
    
    @Override
    public boolean subset(SetOf<T> other) {
        Boolean found = false;
        for(T d : this){
            for(T t: other){
                if(d.equalTo(t)){
                    found = true;
                    break;
                }
            }
            if(found){
                found = false;
            }else
                return false;
        }
        return true;
    }
    
    @Override
    public SetOf<T> union(SetOf<T> other) {
        SetOf<T> newSetOf = new ASet<T>();
        for(T e : this){
            newSetOf.add(e);
        }
        for(T e : other){
            newSetOf.add(e);
        }
        return newSetOf;
    }
    
    @Override
    public SetOf<T> intersection(SetOf<T> other) {
        SetOf<T> newSetOf = new ASet<T>();
        for(T t : this){
            for(T e : other){
                if(e.equalTo(t)){
                    newSetOf.add(e);
                    break;
                }
            }
        }
        return newSetOf;
    }
    
    @Override
    public SetOf<T> difference(SetOf<T> other) {
        SetOf<T> newSetOf = new ASet<T>();
        SetOf<T> interSection = this.intersection(other);
        Boolean found = false;
        for(T t : this){
            for(T e : interSection){
                if(t.equalTo(e)){
                    found = true;
                    break;
                }
            }
            if(found){
                found = false;
            }else{
                newSetOf.add(t);
            }
        }
        return newSetOf;
    }

    public Boolean lessThan(SetOf<T> t) {
        return (this.subset(t) && !this.equalTo(t));
    }
    
    public Boolean equalTo(SetOf<T> t) {
        return this.subset(t) && t.subset(this);
    }
    @Override
    public Iterator<T> iterator() {
        return arrayList.iterator();
    }

    @Override
    public String toString() {
        return arrayList.toString();
    }
    
    
}


/*************************************************************
 
 Step 5.
 Implement a very simple class, A, that implements LessThanOrEqualTo, such that

 - its constructor takes an integer parameter x.
 
 - the lessThan() and equalTo() methods should return a result based on the
 is based on the value of x that the objects being compared were created with. 
   For example,
     A a1 = new A(7);
     A a2 = new A(10);
     boolean c = a1.equalTo(a2);   // False
     c = a1.lessThan(a2);  // True

 - You should override the toString() method so that an A object prints something sensible.
   For example, "System.out.println(new A(3)) should print something like "A[3]".

*************************************************************/


class A implements LessThanOrEqualTo<A>{
    Integer x;
    A (Integer x){
        this.x = x;
    }

    @Override
    public Boolean lessThan(A t) {
        return x < t.x;
    }

    @Override
    public Boolean equalTo(A t) {
        return x == t.x;
    }

    @Override
    public String toString() {
        return "A["+x+"]";
    }
    
}

/*************************************************************

Step 6.
Implement a simple class B that derives from A, such that:
- the constructor takes two integer parameters, x and y.
- within the equalTo() and lessThan(), the comparison involving a
- B object should be based on the sum of x and y.  For example, 
     A a = new A(7);
     B b1 = new B(5,6);
     B b2 = new B(3,4);
     boolean c = a.lessThan(b1);  //TRUE, because 7 < (5+6)
     c = b2.equalTo(a); // TRUE, because (3+4) == 7
     c = b1.lessThan(b2); //FALSE becuase (5+6) is not less than (3+4)
- You should override the toString() method so that printing a B object indicates
  the x and y values it was created with. For example, 
     System.out.println(new B(3,4));
  should print something like B[3,4].

*************************************************************/

class B extends A{
    Integer y;
    Integer z;
    B (Integer y, Integer z) {
        super(y+z);
        this.y = y;
        this.z =z;
    }

    @Override
    public String toString() {
        return "B["+y+","+z+"]";
    }    
}


/*************************************************************

Step 7.

Create a class Part1 that contains the following:

- A static generic method powerSet() that takes as a parameter a set of type SetOf<T>, 
  where T is the generic type parameter (with the appropriate
  constraints) to powerSet.  Since the powerset of a set S is the set of all subsets
  of S, the return type of powerSet() should be SetOf<SetOf<T>>.

  Here is a simple recursive algorithm for the powerset function expressed in ML (using 
  lists to represent sets):

   fun pow [] = [[]]
    |  pow (x::xs) = let val powxs = pow xs
                     in (map (fn mem => x::mem) powxs) @ powxs
                     end
  Hint: In your Java code, you can use get() to pick an element x out of the set, 
  use difference to compute xs (i.e. the set without x), and union insread of @.

- The class also contains a test() method and a main() method that I have provided,
  below. Do not change the code in test() or main(). 

************************************************************/


class Part1 {
    // You can define helper procedures here, if you like.
    static <T extends LessThanOrEqualTo<T>> void MyMap (SetOf<T> firstElement, SetOf<SetOf<T>> restElement, SetOf<SetOf<T>> set){
        if(restElement.empty()){
            set.add(firstElement);
            return;
        }
        SetOf<T> first = restElement.get();
        SetOf<SetOf<T>> setWithElement = new ASet<SetOf<T>>();
        setWithElement.add(first);
        SetOf<SetOf<T>> setWithoutElement = restElement.difference(setWithElement);
        set.add(first.union(firstElement));
        MyMap(firstElement, setWithoutElement, set);   
    }
    //Define powerSet() here
    static <T extends LessThanOrEqualTo<T>> SetOf<SetOf<T>> powerSet(SetOf<T> other) {
        SetOf<SetOf<T>> sets = new ASet<SetOf<T>>();
        if(other.empty()){
            sets.add(other);
            return sets;
        }
        SetOf<T> setWithElement = new ASet<T>();
        setWithElement.add(other.get());
        SetOf<T> setWithoutElement = other.difference(setWithElement);
        sets = powerSet(setWithoutElement);
        SetOf<SetOf<T>> powerSetSetWithoutElement = new ASet<SetOf<T>>();
        MyMap(setWithElement, sets, powerSetSetWithoutElement);
        SetOf<SetOf<T>> newSet = sets.union(powerSetSetWithoutElement);
        return newSet;
    }


/*************************************************************

When this program runs and test() is called, the output should look something like:

s1 = [A[0], A[2], A[4], A[8]]
pow(s1) =[[], [A[8]], [A[4]], [A[8], A[4]], [A[2]], [A[8], A[2]], [A[4], A[2]], [A[8], A[4], A[2]], [A[0]], [A[8], A[0]], [A[4], A[0]], [A[8], A[4], A[0]], [A[2], A[0]], [A[8], A[2], A[0]], [A[4], A[2], A[0]], [A[8], A[4], A[2], A[0]]]
s2 = [B(1,2), B(2,2), B(3,3)]
s1.union(s2) = [A[0], A[2], A[4], A[8], B(1,2), B(3,3)]
s1.difference(s2) = [A[0], A[2], A[8]]
s1.intersection(s2) = [B(2,2)]
s3 = [A[0], A[2], A[8]]
s3.lessThan(s1) is true
s1.subset(s1) is true
s1.lessThan(s1) is false
s1.equalTo(s1.union(s1)) is true
[B(1,2), B(2,2), B(3,3)] equalTo [A[3], A[4], A[6]] is true

************************************************************/

    static void test() {
	SetOf<A> s1 = new ASet<A>();
	for(int i = 0; i < 6; i += 2) {
	    s1.add(new A(i));
	    s1.add(new A(i*2));
	}
	System.out.println("s1 = "+ s1);
	System.out.println("pow(s1) =" + powerSet(s1));

	SetOf<A> s2 = new ASet<A>();
	s2.add(new B(1,2));
	s2.add(new B(2,2));
	s2.add(new B(3,3));
	System.out.println("s2 = " + s2);
	System.out.println("s1.union(s2) = " + s1.union(s2));
	System.out.println("s1.difference(s2) = " + s1.difference(s2));
	System.out.println("s1.intersection(s2) = " + s1.intersection(s2));
	SetOf<A> s3 = s1.difference(s2);
	System.out.println("s3 = " + s3);
	System.out.println("s3.lessThan(s1) is " + s3.lessThan(s1));
	System.out.println("s1.subset(s1) is " + s1.subset(s1));
	System.out.println("s1.lessThan(s1) is " + s1.lessThan(s1));
	System.out.println("s1.equalTo(s1.union(s1)) is " + s1.equalTo(s1.union(s1)));
	SetOf<A> s4 = new ASet<A>();
	s4.add(new A(3));
	s4.add(new A(4));
	s4.add(new A(6));
	System.out.println(s2 + " equalTo " + s4 + " is " + s2.equalTo(s4));

    }
    class C{}
    class D extends C{}
    C c = new D(); 


    public static void main(String[] args) {
	test();
    }
}

