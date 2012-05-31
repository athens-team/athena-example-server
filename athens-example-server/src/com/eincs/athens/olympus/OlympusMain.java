/*
 * Copyright 2012 Athens Team
 *
 * This file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.eincs.athens.olympus;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eincs.athens.core.TransferClients;
import com.eincs.athens.core.TransferClient.AnalyzeReportHandler;
import com.eincs.athens.message.AthensReport;
import com.eincs.athens.olympus.service.AccessTokenService;
import com.eincs.athens.olympus.service.ConfirmService;
import com.eincs.athens.olympus.service.HelloService;
import com.eincs.athens.olympus.service.PostDeleteService;
import com.eincs.athens.olympus.service.PostWriteService;
import com.eincs.athens.olympus.service.TimelineService;
import com.eincs.pantheon.handler.service.simple.SimpleServices;

/**
 * @author roth2520@gmail.com
 */
public class OlympusMain {

	private static final Logger logger = LoggerFactory
			.getLogger(OlympusMain.class);
	public static void main(String[] args) {
		
		// report handler
		TransferClients.addReportListener(new AnalyzeReportHandler() {
			@Override
			public void handlerReport(AthensReport report) {
				logger.info("recv {}", report);	
			}
		});
		
		SimpleServices services = new SimpleServices();
		services.putByAnnotation(new TimelineService());
		services.putByAnnotation(new PostWriteService());
		services.putByAnnotation(new PostDeleteService());
		services.putByAnnotation(new AccessTokenService());
		services.putByAnnotation(new ConfirmService());
		services.putByAnnotation(new HelloService());
		
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(), 
                        Executors.newCachedThreadPool()));

        // Set up the event pipeline factory.
        bootstrap.setPipelineFactory(new OlympusPipelineFactory(services));

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(8080));
	}
}
