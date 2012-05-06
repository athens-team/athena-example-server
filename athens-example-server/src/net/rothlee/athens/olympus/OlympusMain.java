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
package net.rothlee.athens.olympus;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.rothlee.athens.handler.service.simple.SimpleServices;
import net.rothlee.athens.olympus.service.PostDeleteService;
import net.rothlee.athens.olympus.service.PostWriteService;
import net.rothlee.athens.olympus.service.ConfirmService;
import net.rothlee.athens.olympus.service.AccessTokenService;
import net.rothlee.athens.olympus.service.TimelineService;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author roth2520@gmail.com
 */
public class OlympusMain {

	public static void main(String[] args) {
		
		SimpleServices services = new SimpleServices();
		services.putByAnnotation(new TimelineService());
		services.putByAnnotation(new PostWriteService());
		services.putByAnnotation(new PostDeleteService());
		services.putByAnnotation(new AccessTokenService());
		services.putByAnnotation(new ConfirmService());
		
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
