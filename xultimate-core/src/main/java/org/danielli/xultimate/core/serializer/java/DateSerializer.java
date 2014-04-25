package org.danielli.xultimate.core.serializer.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.danielli.xultimate.core.serializer.ClassTypeSupporterSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.util.reflect.ClassUtils;

public class DateSerializer implements ClassTypeSupporterSerializer {

	private Boolean packZeros = true;
	
	public void setPackZeros(Boolean packZeros) {
		this.packZeros = packZeros;
	}
	
	@Override
	public boolean support(Class<?> classType) {
		return ClassUtils.isAssignable(Date.class, classType);
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		return SerializerUtils.encodeLong(((Date) source).getTime(), packZeros);
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeLong(((Date) source).getTime(), false));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		return clazz.cast(new Date(SerializerUtils.decodeLong(bytes)));
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.LONG_BYTE_SIZE];
			inputStream.read(result);
			return clazz.cast(new Date(SerializerUtils.decodeLong(result)));
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}

}
