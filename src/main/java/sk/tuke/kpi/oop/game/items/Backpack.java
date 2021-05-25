package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer <Collectible> {

     private int capacity;
     private String name;
     private List<Collectible>list;

    public Backpack(String name, int capacity){
        this.capacity=capacity;
        this.name=name;
        list=new ArrayList<>();
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(list);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if (list.size()==capacity)throw new IllegalStateException(name+" is full ");
        else list.add(actor);
    }

    @Override
    public void remove(@NotNull Collectible actor) {

        if(list.isEmpty()){
            return;
        }
        list.remove(actor);
    }

    @Nullable
    @Override
    public Collectible peek() {
        if(list.isEmpty()){
            return null;
        }
        return list.get(list.size()-1);
    }

    @Override
    public void shift() {
        Collections.rotate(list,1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return list.iterator();
    }
}
