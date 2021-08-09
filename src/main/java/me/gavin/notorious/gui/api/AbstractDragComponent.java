package me.gavin.notorious.gui.api;

public abstract class AbstractDragComponent extends AbstractComponent {

    private int dragX;
    private int dragY;
    public boolean dragging;

    public AbstractDragComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void startDragging(int mouseX, int mouseY) {
        if (this.isMouseInside(mouseX, mouseY)) {
            this.dragging = true;
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
        }

    }

    public void stopDragging(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public void updateDragPosition(int mouseX, int mouseY) {
        if (this.dragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }

    }
}
