package cn.ezandroid.aq.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开局库
 *
 * @author like
 * @date 2019-03-08
 */
public class OpeningBook implements Serializable {

    private static final long serialVersionUID = 42L;

    private Map<Long, List<Forecast>> mBookTable = new HashMap<>();

    private byte mBoardSize;

    public OpeningBook(byte boardSize) {
        mBoardSize = boardSize;
    }

    public OpeningBook(byte boardSize, Map<Long, List<Forecast>> table) {
        mBoardSize = boardSize;
        mBookTable.putAll(table);
    }

    /**
     * 对于某个局面的落子预测
     */
    public static class Forecast implements Serializable {

        private static final long serialVersionUID = 42L;

        private short mPosition;
        private short mValue; // 48.92 -> 4892, 48.921 -> 4892, 48.9 -> 4890

        public Forecast(short position, short value) {
            mPosition = position;
            mValue = value;
        }

        public short getPosition() {
            return mPosition;
        }

        public short getValue() {
            return mValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Forecast)) return false;

            Forecast forecast = (Forecast) o;

            return mPosition == forecast.mPosition;
        }

        @Override
        public int hashCode() {
            return (int) mPosition;
        }
    }

    /**
     * 将对于某个局面的落子预测列表添加到开局库中
     *
     * @param hash
     * @param forecasts
     */
    public void add(long hash, List<Forecast> forecasts) {
        List<Forecast> cache = mBookTable.get(hash);
        if (cache != null) {
            cache.addAll(forecasts);
        } else {
            mBookTable.put(hash, forecasts);
        }
    }

    /**
     * 将对于某个局面的落子预测添加到开局库中
     *
     * @param hash
     * @param forecast
     */
    public void add(long hash, Forecast forecast) {
        List<Forecast> cache = mBookTable.get(hash);
        if (cache != null) {
            cache.add(forecast);
        } else {
            cache = new ArrayList<>();
            cache.add(forecast);
            mBookTable.put(hash, cache);
        }
    }

    /**
     * 获取对于某个局面的落子预测列表
     *
     * @param hash
     * @return
     */
    public List<Forecast> get(long hash) {
        return mBookTable.get(hash);
    }

    /**
     * 获取开局库局面数
     *
     * @return
     */
    public int size() {
        return mBookTable.size();
    }

    public byte getBoardSize() {
        return mBoardSize;
    }

    Map<Long, List<Forecast>> getBookTable() {
        return mBookTable;
    }
}
