package com.javafans.listener;

import java.util.List;

import javax.batch.api.chunk.listener.ItemWriteListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.javafans.common.LoggerConstants;

@Component
public class ItemWriterListener implements ItemWriteListener {

	Logger logger=LoggerFactory.getLogger(ItemWriteListener.class);

	@Override
	public void beforeWrite(List<Object> items) throws Exception {
		logger.info(LoggerConstants.START_WRITING_FILE);
	}

	@Override
	public void afterWrite(List<Object> items) throws Exception {
		logger.info(LoggerConstants.END_WRITING_FILE);
	}

	@Override
	public void onWriteError(List<Object> items, Exception ex) throws Exception {
		
	}

}
