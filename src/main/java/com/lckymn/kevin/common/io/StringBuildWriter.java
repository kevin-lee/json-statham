/**
 * 
 */
package com.lckymn.kevin.common.io;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-18)
 */
public final class StringBuildWriter extends Writer
{
	private final StringBuilder builder;

	public StringBuildWriter()
	{
		this.builder = new StringBuilder();
		lock = builder;
	}

	public StringBuildWriter(final int initialSize)
	{
		if (0 > initialSize)
		{
			throw new IllegalArgumentException("Negative int is entered as a builder initial size");
		}
		builder = new StringBuilder(initialSize);
		lock = builder;
	}

	private void write0(int c)
	{
		builder.append((char) c);

	}

	@Override
	public void write(int c) throws IOException
	{
		write0(c);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException
	{
		if ((0 > off) || (cbuf.length < off) || (0 > len) || (cbuf.length < (off + len)) || (0 > (off + len)))
		{
			throw new IndexOutOfBoundsException();
		}
		else if (0 == len)
		{
			return;
		}
		builder.append(cbuf, off, len);

	}

	private void write0(String str)
	{
		builder.append(str);

	}

	@Override
	public void write(String str) throws IOException
	{
		write0(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException
	{
		builder.append(str.substring(off, off + len));
	}

	@Override
	public StringBuildWriter append(CharSequence csq) throws IOException
	{
		write0(String.valueOf(csq));
		return this;
	}

	@Override
	public StringBuildWriter append(final CharSequence csq, final int start, final int end) throws IOException
	{
		final CharSequence charSequence = null == csq ? "null" : csq;
		write0(charSequence.subSequence(start, end)
				.toString());
		return this;
	}

	@Override
	public StringBuildWriter append(char c) throws IOException
	{
		write0(c);
		return this;
	}

	@Override
	public String toString()
	{
		return builder.toString();
	}

	public StringBuilder getStringBuilder()
	{
		return builder;
	}

	@Override
	public void flush() throws IOException
	{
	}

	@Override
	public void close() throws IOException
	{
	}

}
