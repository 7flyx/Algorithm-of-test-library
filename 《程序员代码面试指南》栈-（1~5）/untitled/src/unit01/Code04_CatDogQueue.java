package unit01;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.io.InputStreamReader;

public class Code04_CatDogQueue {

    public static class Pet {
        private String type;
        private int x; //这种动物的数量

        public Pet(String type, int x) {
            this.type = type;
            this.x = x;
        }

        public String getPetType() {
            return type;
        }

        public int getX() {
            return x;
        }
    }

    public static class Cat extends Pet {
        public Cat(int x) {
            super("cat",x);
        }
    }

    public static class Dog extends Pet {
        public Dog(int x) {
            super("dog",x);
        }
    }

    public static class PetEnterQueue {
        private Pet pet;
        private long count; //这个动物进队的时间顺序

        public PetEnterQueue(Pet pet, long count) {
            this.pet = pet;
            this.count = count;
        }

        public Pet getPet() {
            return pet;
        }

        public long getCount() {
            return count;
        }

        public String getPetEnterQueueType() {
            return pet.getPetType();
        }
    }

    public static class CatDogQueue {
        private Queue<PetEnterQueue> dogQ;
        private Queue<PetEnterQueue> catQ;
        private long count; //达到相应的进队先后顺序

        public CatDogQueue() {
            dogQ = new LinkedList<>();
            catQ = new LinkedList<>();
            this.count = 0;
        }

        public void add(Pet pet) { //以包装类的形式进行添加
            //是什么动物，就添加到相应的队列中
            if (pet.getPetType().equals("dog")) {
                dogQ.add(new PetEnterQueue(pet, this.count++));
            } else if (pet.getPetType().equals("cat")) {
                catQ.add(new PetEnterQueue(pet, this.count++));
            } else {
                throw new RuntimeException("error,no cat or dog.");
            }
        }

        public Pet pollAll() {
            if (!catQ.isEmpty() && !dogQ.isEmpty()) {
                if (catQ.peek().count < dogQ.peek().count) { //谁先进入队列，就先弹出谁
                    return catQ.poll().getPet();
                } else {
                    return dogQ.poll().getPet();
                }
            } else if (!catQ.isEmpty()) {
                return catQ.poll().getPet();
            } else if (!dogQ.isEmpty()) {
                return dogQ.poll().getPet();
            } else {
                throw new RuntimeException("your queue is empty.");
            }
        }

        public Dog pollDog() {
            if (!dogQ.isEmpty()) {
                return (Dog)dogQ.poll().getPet();
            } else {
                throw new RuntimeException("your dogQueue is empty.");
            }
        }

        public Cat pollCat() {
            if (!catQ.isEmpty()) {
                return (Cat)catQ.poll().getPet();
            } else {
                throw new RuntimeException("your catQueue is empty.");
            }
        }

        public boolean isEmpty() {
            return dogQ.isEmpty() && catQ.isEmpty();
        }

        public boolean isDogEmpty() {
            return dogQ.isEmpty();
        }

        public boolean isCatEmpty() {
            return catQ.isEmpty();
        }
    }

    public static void main(String[] args) throws IOException {
        /*
                牛客网： 猫狗队列，实则输出就是数量，而非是一个个的猫或狗添加到队列中，只是添加的一个数量
                而书上写的是，返回的是一个Pet类
         */
        CatDogQueue queue = new CatDogQueue();
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(sc.readLine());
        Pet pet = null;

        for (int i = 0; i < N; i++) {

            String[] inputData = sc.readLine().split(" ");
            switch(inputData[0]) {
                case "add":
                    if ("dog".equals(inputData[1])) {
                        queue.add(new Dog(Integer.parseInt(inputData[2])));
                    } else if ("cat".equals(inputData[1])) {
                        queue.add(new Cat(Integer.parseInt(inputData[2])));
                    } else {
                        throw new RuntimeException("no the animal.");
                    }
                    break;
                case "pollAll":
                    while (!queue.isEmpty()) {
                        pet = queue.pollAll();
                        System.out.println(pet.getPetType() + " " + pet.getX());
                    }
                    break;
                case "pollDog":
                    while (!queue.isDogEmpty()) {
                        pet = queue.pollDog();
                        System.out.println(pet.getPetType() + " " + pet.getX());
                    }
                    break;
                case "pollCat":
                    while (!queue.isCatEmpty()) {
                        pet = queue.pollCat();
                        System.out.println(pet.getPetType() + " " + pet.getX());
                    }
                    break;
                case "isEmpty":
                    System.out.println(queue.isEmpty()? "yes" : "no");
                    break;
                case "isCatEmpty":
                    System.out.println(queue.isCatEmpty()? "yes" : "no");
                    break;
                case "isDogEmpty":
                    System.out.println(queue.isDogEmpty()? "yes" : "no");
                    break;
            }
        }
        sc.close();
    }
}
