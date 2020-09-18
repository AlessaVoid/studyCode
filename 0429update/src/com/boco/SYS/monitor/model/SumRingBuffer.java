package com.boco.SYS.monitor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * web端缓存监控信息
 * @Author zhuhongjiang
 * @Date 2020/1/6 下午2:51
 **/
public class SumRingBuffer {

	private static final Logger log = LoggerFactory.getLogger(SumRingBuffer.class);

	private TradeReport[][] ringBuffer;
	private int bufferSize;

	public SumRingBuffer(int bufferSize, int totalMaster) {
		TradeReport[][] array = new TradeReport[bufferSize][totalMaster];
		for (int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				array[i][j] = new TradeReport();
			}
		}

		this.bufferSize = bufferSize;
		this.ringBuffer = array;
	}

	/**
	 * 获取指定位置信息
	 * @Author zhuhongjiang
	 * @Date 2020/1/6 下午2:51
	 **/
	public TradeReport[] get(int index) {
		return ringBuffer[index];
	}

	/**
	 * 向指定位置存放信息
	 * @Author zhuhongjiang
	 * @Date 2020/1/6 下午2:51
	 **/
	public void put(int index, int masterIndex, TradeReport object) {
		TradeReport tradeReportBuffer = this.get(index)[masterIndex];
		BeanUtils.copyProperties(object, tradeReportBuffer);
		tradeReportBuffer.setReset(1);
	}

}
