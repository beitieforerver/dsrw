package springold.support;

public interface Pageable {
    /**
     * 总对象数。
     * @return
     */
    int getItemCount();
    /**
     * 页大小。
     * @return
     */
    int getPageSize();

    /**
     * 开始索引号。
     * @return
     */
    int getStartIndex();
}
