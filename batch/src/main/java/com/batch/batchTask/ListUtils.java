package com.batch.batchTask;


import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ListUtils
 * @Author: 青衣醉
 * @Date: 2023/3/23 4:13 下午
 */
public class ListUtils {
    public static <T>List<List<T>> splitList(List<T> list, int splitNum){
        if(list==null || list.size ()<=0 || splitNum<=0){
            return new ArrayList<> ();
        }
        List<List<T>> listList = new ArrayList<> ();
        //
        int listSize = list.size ();
        //余数
        int remaider = listSize%splitNum;
        //商
        int listCount = listSize/splitNum;
        //偏移量
        int offset = 0;
        for (int i=0;i<splitNum;remaider--,i++){
            int num= offset+listCount;
            if (remaider>0){
                num= num+1;
            }
            listList.add (list.subList (offset, num>listSize?listSize:num));
            offset=num;
        }
        return listList;
    }

    // 假设要拆分的list为listToSplit，拆分后的每个子list长度为splitSize
    public static <T>List<List<T>> splitList2(List<T> listToSplit, int splitSize) {
        List<List<T>> listList = new ArrayList<> ();
        for (int i = 0; i < listToSplit.size(); i += splitSize) {
            listList.add(listToSplit.subList(i, Math.min(i + splitSize, listToSplit.size())));
        }
        return listList;
    }
}
