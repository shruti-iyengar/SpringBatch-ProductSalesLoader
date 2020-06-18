package com.javafans.listener;

import javax.batch.api.chunk.listener.ItemReadListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.javafans.common.LoggerConstants;

@Component
public class ItemReaderListener implements ItemReadListener {
	
	Logger logger=LoggerFactory.getLogger(ItemReadListener.class);
	
	@Override
	public void beforeRead() throws Exception {
		logger.info(LoggerConstants.START_READING_FILE);
	}

	@Override
	public void afterRead(Object item) throws Exception {
		logger.info(LoggerConstants.END_READING_FILE);
	}

	@Override
	public void onReadError(Exception ex) throws Exception {
		
	}

}
