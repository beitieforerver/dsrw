package springold.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author：Weitj
 * @Description：
 * @Date： 2022/01/20 17:57
 * @Version 1.0
 */
public class PageableList<E> extends ArrayList<E> implements List<E>,Pageable {
    private final int pageSize;
    private final int itemCount;
    private final int startIndex;
    public PageableList(Collection<? extends E> c, int startIndex, int pageSize, int itemCount) {
        super(c);
        this.itemCount = itemCount;
        this.pageSize = pageSize;
        this.startIndex = startIndex;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getStartIndex() {
        return startIndex;
    }
}
