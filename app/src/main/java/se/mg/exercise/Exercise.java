package se.mg.exercise;

import se.mg.entity.BaseEntity;

public class Exercise extends BaseEntity {
   private static final long serialVersionUID = 1L;
   private String id;
   private String name;
   private String note;

   public Exercise(String id, String name, Category category, Level level, String note, long version) {
      super(version);
      this.id = id;
      this.name = name;
      this.note = note;
      this.level = level;
      this.category = category;
   }

   public String getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }
}
