/**
 * 
 */
package com.lckymn.kevin.common.reflect;

import static com.lckymn.kevin.common.validation.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lckymn.kevin.common.util.CommonConstants;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-17)
 */
public final class Classes
{
	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	private Classes()
	{
		throw new IllegalStateException(getClass().getName() + CommonConstants.CANNOT_BE_INSTANTIATED);
	}

	private static <S, T extends S> void extractAllClassesInSubToSuperOrder(Class<? super T> targetClass,
			Class<S> beforeClass, Collection<Class<? super T>> classCollection)
	{
		if (null == targetClass || targetClass.equals(beforeClass) || targetClass.equals(Object.class))
		{
			return;
		}
		classCollection.add(targetClass);
		extractAllClassesInSubToSuperOrder(targetClass.getSuperclass(), beforeClass, classCollection);
	}

	public static <S, T extends S> void extractSuperClassesInSubToSuperOrder(Class<T> targetClass,
			Class<S> beforeClass, boolean includeTargetClass, Collection<Class<? super T>> classCollection)
	{
		if (includeTargetClass)
		{
			extractAllClassesInSubToSuperOrder(targetClass, beforeClass, classCollection);
		}
		else
		{
			extractAllClassesInSubToSuperOrder(targetClass.getSuperclass(), beforeClass, classCollection);
		}
	}

	public static <S, T extends S> List<Class<? super T>> extractSuperClassesInSubToSuperOrder(Class<T> targetClass,
			Class<S> beforeClass, boolean includeTargetClass)
	{
		final List<Class<? super T>> classList = new ArrayList<Class<? super T>>();
		extractSuperClassesInSubToSuperOrder(targetClass, beforeClass, includeTargetClass, classList);
		return classList;
	}

	private static <S, T extends S> void extractAllClassesInSuperToSubOrder(Class<? super T> targetClass,
			Class<S> beforeClass, Collection<Class<? super T>> classCollection)
	{
		if (null == targetClass || targetClass.equals(beforeClass) || targetClass.equals(Object.class))
		{
			return;
		}
		extractAllClassesInSuperToSubOrder(targetClass.getSuperclass(), beforeClass, classCollection);
		classCollection.add(targetClass);
	}

	public static <S, T extends S> void extractSuperClassesInSuperToSubOrder(Class<T> targetClass,
			Class<S> beforeClass, boolean includeTargetClass, Collection<Class<? super T>> classCollection)
	{
		if (includeTargetClass)
		{
			extractAllClassesInSuperToSubOrder(targetClass, beforeClass, classCollection);
		}
		else
		{
			extractAllClassesInSuperToSubOrder(targetClass.getSuperclass(), beforeClass, classCollection);
		}
	}

	public static <S, T extends S> List<Class<? super T>> extractSuperClassesInSuperToSubOrder(Class<T> targetClass,
			Class<S> beforeClass, boolean includeTargetClass)
	{
		final List<Class<? super T>> classList = new ArrayList<Class<? super T>>();
		extractSuperClassesInSuperToSubOrder(targetClass, beforeClass, includeTargetClass, classList);
		return classList;
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSubToSuperOrder0(
			final Class<? super T> targetClass, final Class<S> beforeClass, final Class<? extends A> annotation,
			final Collection<Class<? super T>> classCollection)
	{
		if (null == targetClass || targetClass.equals(beforeClass) || targetClass.equals(Object.class))
		{
			return;
		}
		if (targetClass.isAnnotationPresent(annotation))
		{
			classCollection.add(targetClass);
		}
		extractClssesWithAnnotationsInSubToSuperOrder0(targetClass.getSuperclass(), beforeClass, annotation,
				classCollection);
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSubToSuperOrder0(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation)
	{
		assertNotNull(annotation, "The annotation must not be null.");
		if (includeTargetClass)
		{
			extractClssesWithAnnotationsInSubToSuperOrder0(targetClass, beforeClass, annotation, classCollection);
		}
		else
		{
			extractClssesWithAnnotationsInSubToSuperOrder0(targetClass.getSuperclass(), beforeClass, annotation,
					classCollection);
		}
	}

	public static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSubToSuperOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation)
	{
		extractClssesWithAnnotationsInSubToSuperOrder0(targetClass, beforeClass, includeTargetClass, classCollection,
				annotation);
	}

	public static <S, T extends S, A extends Annotation> List<Class<? super T>> extractClssesWithAnnotationsInSubToSuperOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Class<? extends A> annotation)
	{
		final List<Class<? super T>> classList = new ArrayList<Class<? super T>>();
		extractClssesWithAnnotationsInSubToSuperOrder0(targetClass, beforeClass, includeTargetClass, classList,
				annotation);
		return classList;
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSubToSuperOrder0(
			final Class<? super T> targetClass, final Class<S> beforeClass, final Class<? extends A> annotation,
			final Class<? extends A>[] remainingAnnotations, final Collection<Class<? super T>> classCollection)
	{
		if (null == targetClass || targetClass.equals(beforeClass) || targetClass.equals(Object.class))
		{
			return;
		}
		if (targetClass.isAnnotationPresent(annotation))
		{
			classCollection.add(targetClass);
		}
		else
		{
			for (Class<? extends A> eachAnnotation : remainingAnnotations)
			{
				if (targetClass.isAnnotationPresent(eachAnnotation))
				{
					classCollection.add(targetClass);
					break;
				}
			}
		}
		extractClssesWithAnnotationsInSubToSuperOrder0(targetClass.getSuperclass(), beforeClass, annotation,
				remainingAnnotations, classCollection);
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSubToSuperOrder0(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation,
			final Class<? extends A>... remainingAnnotations)
	{
		if (includeTargetClass)
		{
			extractClssesWithAnnotationsInSubToSuperOrder0(targetClass, beforeClass, annotation, remainingAnnotations,
					classCollection);
		}
		else
		{
			extractClssesWithAnnotationsInSubToSuperOrder0(targetClass.getSuperclass(), beforeClass, annotation,
					remainingAnnotations, classCollection);
		}
	}

	public static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSubToSuperOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation,
			final Class<? extends A>... remainingAnnotations)
	{
		extractClssesWithAnnotationsInSubToSuperOrder0(targetClass, beforeClass, includeTargetClass, classCollection,
				annotation, remainingAnnotations);
	}

	public static <S, T extends S, A extends Annotation> List<Class<? super T>> extractClssesWithAnnotationsInSubToSuperOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Class<? extends A> annotation, final Class<? extends A>... remainingAnnotations)
	{
		final List<Class<? super T>> classList = new ArrayList<Class<? super T>>();
		extractClssesWithAnnotationsInSubToSuperOrder0(targetClass, beforeClass, includeTargetClass, classList,
				annotation, remainingAnnotations);
		return classList;
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSuperToSubOrder0(
			final Class<? super T> targetClass, final Class<S> beforeClass, final Class<? extends A> annotation,
			final Collection<Class<? super T>> classCollection)
	{
		if (null == targetClass || targetClass.equals(beforeClass) || targetClass.equals(Object.class))
		{
			return;
		}
		extractClssesWithAnnotationsInSuperToSubOrder0(targetClass.getSuperclass(), beforeClass, annotation,
				classCollection);
		if (targetClass.isAnnotationPresent(annotation))
		{
			classCollection.add(targetClass);
		}
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSuperToSubOrder0(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation)
	{
		assertNotNull(annotation, "The annotation must not be null.");
		if (includeTargetClass)
		{
			extractClssesWithAnnotationsInSuperToSubOrder0(targetClass, beforeClass, annotation, classCollection);
		}
		else
		{
			extractClssesWithAnnotationsInSuperToSubOrder0(targetClass.getSuperclass(), beforeClass, annotation,
					classCollection);
		}
	}

	public static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSuperToSubOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation)
	{
		extractClssesWithAnnotationsInSuperToSubOrder0(targetClass, beforeClass, includeTargetClass, classCollection,
				annotation);
	}

	public static <S, T extends S, A extends Annotation> List<Class<? super T>> extractClssesWithAnnotationsInSuperToSubOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Class<? extends A> annotation)
	{
		final List<Class<? super T>> classList = new ArrayList<Class<? super T>>();
		extractClssesWithAnnotationsInSuperToSubOrder0(targetClass, beforeClass, includeTargetClass, classList,
				annotation);
		return classList;
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSuperToSubOrder0(
			final Class<? super T> targetClass, final Class<S> beforeClass, final Class<? extends A> annotation,
			final Class<? extends A>[] remainingAnnotations, final Collection<Class<? super T>> classCollection)
	{
		if (null == targetClass || targetClass.equals(beforeClass) || targetClass.equals(Object.class))
		{
			return;
		}
		extractClssesWithAnnotationsInSuperToSubOrder0(targetClass.getSuperclass(), beforeClass, annotation,
				remainingAnnotations, classCollection);
		if (targetClass.isAnnotationPresent(annotation))
		{
			classCollection.add(targetClass);
		}
		else
		{
			for (Class<? extends A> eachAnnotation : remainingAnnotations)
			{
				if (targetClass.isAnnotationPresent(eachAnnotation))
				{
					classCollection.add(targetClass);
					break;
				}
			}
		}
	}

	private static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSuperToSubOrder0(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation,
			final Class<? extends A>... remainingAnnotations)
	{
		if (includeTargetClass)
		{
			extractClssesWithAnnotationsInSuperToSubOrder0(targetClass, beforeClass, annotation, remainingAnnotations,
					classCollection);
		}
		else
		{
			extractClssesWithAnnotationsInSuperToSubOrder0(targetClass.getSuperclass(), beforeClass, annotation,
					remainingAnnotations, classCollection);
		}
	}

	public static <S, T extends S, A extends Annotation> void extractClssesWithAnnotationsInSuperToSubOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Collection<Class<? super T>> classCollection, final Class<? extends A> annotation,
			final Class<? extends A>... remainingAnnotations)
	{
		extractClssesWithAnnotationsInSuperToSubOrder0(targetClass, beforeClass, includeTargetClass, classCollection,
				annotation, remainingAnnotations);
	}

	public static <S, T extends S, A extends Annotation> List<Class<? super T>> extractClssesWithAnnotationsInSuperToSubOrder(
			final Class<T> targetClass, final Class<S> beforeClass, final boolean includeTargetClass,
			final Class<? extends A> annotation, final Class<? extends A>... remainingAnnotations)
	{
		final List<Class<? super T>> classList = new ArrayList<Class<? super T>>();
		extractClssesWithAnnotationsInSuperToSubOrder0(targetClass, beforeClass, includeTargetClass, classList,
				annotation, remainingAnnotations);
		return classList;
	}

	/**
	 * @param <T>
	 * @param targetClass
	 * @param parameterTypes
	 * @return
	 */
	public static <T> Constructor<T> findConstructor(Class<T> targetClass, Class<?>... parameterTypes)
	{
		try
		{
			return targetClass.getDeclaredConstructor(parameterTypes);
		}
		catch (SecurityException e)
		{
			/* Only for debugging */
			// final StringBuildWriter stringBuildWriter = new StringBuildWriter();
			// e.printStackTrace(new PrintWriter(stringBuildWriter));
			// System.out.println(stringBuildWriter.toString());
		}
		catch (NoSuchMethodException e)
		{
			/* Only for debugging */
			// final StringBuildWriter stringBuildWriter = new StringBuildWriter();
			// e.printStackTrace(new PrintWriter(stringBuildWriter));
			// System.out.println(stringBuildWriter.toString());
		}
		return null;
	}

	private static <T, A extends Annotation> Constructor<T> findConstructorWithAnnotation0(final Class<T> targetClass,
			final Class<? extends A> annotation)
	{
		assertNotNull(annotation, "The annotation must not be null.");
		for (Constructor<?> constructor : targetClass.getDeclaredConstructors())
		{
			if (constructor.isAnnotationPresent(annotation))
			{
				@SuppressWarnings("unchecked")
				final Constructor<T> constructorOfT = (Constructor<T>) constructor;
				return constructorOfT;
			}
		}
		return null;
	}

	public static <T, A extends Annotation> Constructor<T> findConstructorWithAnnotation(final Class<T> targetClass,
			final Class<? extends A> annotation)
	{
		return findConstructorWithAnnotation0(targetClass, annotation);
	}

	private static <T, A extends Annotation> Constructor<T> findConstructorWithAnnotation0(final Class<T> targetClass,
			final Class<? extends A> annotation, final Class<? extends A>... remainingAnnotations)
	{
		for (Constructor<?> constructor : targetClass.getDeclaredConstructors())
		{
			if (constructor.isAnnotationPresent(annotation))
			{
				@SuppressWarnings("unchecked")
				final Constructor<T> constructorOfT = (Constructor<T>) constructor;
				return constructorOfT;
			}
			for (Class<? extends A> eachAnnotation : remainingAnnotations)
			{
				if (constructor.isAnnotationPresent(eachAnnotation))
				{
					@SuppressWarnings("unchecked")
					final Constructor<T> constructorOfT = (Constructor<T>) constructor;
					return constructorOfT;
				}
			}
		}
		return null;
	}

	public static <T, A extends Annotation> Constructor<T> findConstructorWithAnnotation(final Class<T> targetClass,
			final Class<? extends A> annotation, final Class<? extends A>... remainingAnnotations)
	{
		return findConstructorWithAnnotation0(targetClass, annotation, remainingAnnotations);
	}

	private static <T, A extends Annotation> Set<Constructor<T>> findAllConstructorsWithAnnotation0(
			Class<T> targetClass, final Class<? extends A> annotation)
	{
		assertNotNull(annotation, "The annotation must not be null.");
		final Set<Constructor<T>> constructorSet = new HashSet<Constructor<T>>();

		for (Constructor<?> constructor : targetClass.getDeclaredConstructors())
		{
			if (constructor.isAnnotationPresent(annotation))
			{
				@SuppressWarnings("unchecked")
				final Constructor<T> constructorOfT = (Constructor<T>) constructor;
				constructorSet.add(constructorOfT);
			}
		}
		return constructorSet;
	}

	public static <T, A extends Annotation> Set<Constructor<T>> findAllConstructorsWithAnnotation(
			final Class<T> targetClass, final Class<? extends A> annotation)
	{
		return findAllConstructorsWithAnnotation0(targetClass, annotation);
	}

	private static <T, A extends Annotation> Set<Constructor<T>> findAllConstructorsWithAnnotation0(
			Class<T> targetClass, final Class<? extends A> annotation, final Class<? extends A>... remainingAnnotations)
	{
		final Set<Constructor<T>> constructorSet = new HashSet<Constructor<T>>();

		for (Constructor<?> constructor : targetClass.getDeclaredConstructors())
		{
			if (constructor.isAnnotationPresent(annotation))
			{
				@SuppressWarnings("unchecked")
				final Constructor<T> constructorOfT = (Constructor<T>) constructor;
				constructorSet.add(constructorOfT);
				continue;
			}
			for (Class<? extends A> eachAnnotation : remainingAnnotations)
			{
				if (constructor.isAnnotationPresent(eachAnnotation))
				{
					@SuppressWarnings("unchecked")
					final Constructor<T> constructorOfT = (Constructor<T>) constructor;
					constructorSet.add(constructorOfT);
					break;
				}
			}
		}
		return constructorSet;
	}

	public static <T, A extends Annotation> Set<Constructor<T>> findAllConstructorsWithAnnotation(
			final Class<T> targetClass, final Class<? extends A> annotation,
			final Class<? extends A>... remainingAnnotations)
	{
		return findAllConstructorsWithAnnotation0(targetClass, annotation, remainingAnnotations);
	}
}
