package net.rothlee.athens.olympus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.rothlee.athens.handler.codec.http.AthensHttpHandler;
import net.rothlee.athens.handler.codec.http.AthensHttpProcessor;
import net.rothlee.athens.handler.service.simple.SimpleAuthHandler;
import net.rothlee.athens.handler.service.simple.SimpleServiceDiscovery;
import net.rothlee.athens.handler.service.simple.SimpleServiceInvoker;
import net.rothlee.athens.handler.service.simple.SimpleServices;
import net.rothlee.athens.test.simple.IndexService;
import net.rothlee.athens.test.simple.MenuService;
import net.rothlee.athens.test.simple.PrintCookieService;
import net.rothlee.athens.test.simple.SetCookieService;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;

public class OlympusPipelineFactory implements ChannelPipelineFactory {

	private final int DEFAULT_WORKER_THREAD_COUNT = Runtime.getRuntime()
			.availableProcessors() * 8;

	private final ExecutorService executor;

	public OlympusPipelineFactory() {
		this.executor = Executors
				.newFixedThreadPool(DEFAULT_WORKER_THREAD_COUNT);
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {

		SimpleServices services = new SimpleServices();
		services.putByAnnotation(new MenuService());
		services.putByAnnotation(new IndexService());
		services.putByAnnotation(new PrintCookieService());
		services.putByAnnotation(new SetCookieService());
		
		return Channels.pipeline(new HttpRequestDecoder(),
				new HttpResponseEncoder(),
				new HttpContentCompressor(),
				new AthensHttpHandler(),
				new ExecutionHandler(executor),
				new AthensHttpProcessor(),
				new SimpleAuthHandler(),
				new SimpleServiceDiscovery(services),
				new SimpleServiceInvoker());
	}
}
