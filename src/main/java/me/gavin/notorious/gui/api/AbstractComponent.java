package me.gavin.notorious.gui.api;

public abstract class AbstractComponent extends Rect {

    public AbstractComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public abstract void render(int i, int j, float f);

    public abstract void mouseClicked(int i, int j, int k);

    public abstract void mouseReleased(int i, int j, int k);

    public abstract void keyTyped(char c0, int i);

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
