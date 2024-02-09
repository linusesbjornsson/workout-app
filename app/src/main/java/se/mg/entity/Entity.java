package se.mg.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
   private final long version;

   public Entity(long version) {
      this.version = version;
   }

   public long getVersion() {
      return version;
   }
}
