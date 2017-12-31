package util.menu;

class MenuItem {
  private int id;
  private String title;
  private Callback cb;

  MenuItem(int id, String title, Callback cb) {
      this.id = id;
      this.title = title;
      this.cb = cb;
  }

  @Override
  public String toString() {
    return this.title;
  }

  void callMethod(int id) {
    cb.callback(id);
  }

  int getID() {
      return this.id;
  }
}
