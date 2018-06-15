import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * spec
 * AF: ID->梯子的唯一标识
 * size-> 当前梯子上爬着的猴子数量（便于在选择过河策略中的实现）
 * direction->当前梯子上所有猴子的过河方向（null->梯子上没有猴子 "R->L"所有猴子的方向都是从右到左 "L->R"所有的猴子方向都是从左到右）
 * rungs-> 一个列表，存储的台阶对象
 * RI: rungs的数量始终等于传入的rungNumber数量 direction的值只能是null,"L->R" ,"R->L"三个值 size>=0
 * safe from exposure:
 * Thread safe:
 */
public class Ladder {
    private final int ID;
    private int size = 0;
    private String direction = null;
    private final List<Rung> rungs = Collections.synchronizedList(new ArrayList<>());

    Ladder(int rungNumber, int ID) {
        this.ID = ID;
        synchronized (rungs) {
            for (int i = 0; i < rungNumber; i++)
                rungs.add(new Rung());
        }
    }

    /**
     * @return 返回梯子的id值
     */
    int getID() {
        return ID;
    }

    /**
     * @return 当前梯子上的猴子的数量
     */
    int getSize() {
        return size;
    }

    /**
     * @return 当前梯子上猴子的行进方向
     */
    String getDirection() {
        return direction;
    }

    /**
     * 删除指定踏板上的某个猴子
     *
     * @param index 踏板的下标
     * @return boolean值，表示是否删除成功
     */
    boolean remove(int index) {
        if (index > rungs.size() - 1 || index < 0)
            return false;
        rungs.get(index).setMonkey(null);
        size--;
        if (size == 0)
            this.direction = null;
        return true;
    }

    /**
     * 在梯子的指定台阶上放置猴子
     * 需要设置猴子的台阶必须原来是空的，否则无法添加成功，会返回false
     * 可以将某个台阶位置置空（传入的monkey对象为空）
     *
     * @param index  指定梯子上的台阶位置
     * @param monkey 需要设置的猴子
     * @return 是否设置成功的boolean值
     */
    boolean addMonkey(int index, Monkey monkey) {
        if (index > rungs.size() - 1 || index < 0)
            return false;
        if (rungs.get(index).setMonkey(monkey)) {
            this.size++;
            this.direction = monkey.getDirection();
            return true;
        }
        return false;
    }

    /**
     * @return 这个梯子上的所有台阶
     */
    List<Rung> getRungs() {
        return new ArrayList<>(this.rungs);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Ladder && ((Ladder) obj).getID() == this.ID;
    }

    @Override
    public int hashCode() {
        int[] IDs = new int[1];
        IDs[0] = this.ID;
        return Arrays.hashCode(IDs);
    }

    @Override
    public String toString() {
        return "Ladder" + this.getID();
    }
}
