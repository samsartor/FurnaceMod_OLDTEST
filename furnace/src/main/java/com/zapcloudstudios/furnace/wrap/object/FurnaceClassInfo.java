package com.zapcloudstudios.furnace.wrap.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.zapcloudstudios.furnace.api.FurnaceI;
import com.zapcloudstudios.furnace.wrap.annotation.APIConst;
import com.zapcloudstudios.furnace.wrap.annotation.APIFunction;
import com.zapcloudstudios.furnace.wrap.annotation.APIGetter;
import com.zapcloudstudios.furnace.wrap.annotation.APIProperty;
import com.zapcloudstudios.furnace.wrap.annotation.APISetter;

public class FurnaceClassInfo
{
	public static Map<Class<? extends FurnaceI>, FurnaceClassInfo> classCache = new HashMap<>();
	
	public static FurnaceClassInfo getInfo(Class<? extends FurnaceI> clazz)
	{
		FurnaceClassInfo info = classCache.get(clazz);
		if (info == null)
		{
			info = new FurnaceClassInfo(clazz);
			classCache.put(clazz, info);
		}
		return info;
	}
	
	public Map<String, FurnacePropInfo> props = new HashMap<>();
	
	FurnaceClassInfo(Class<? extends FurnaceI> clazz)
	{
		this.loadMembers(clazz);
	}
	
	void loadMembers(Class<?> clazz)
	{
		Class<?> parent = clazz.getSuperclass();
		if (parent != null && FurnaceI.class.isAssignableFrom(parent))
		{
			this.loadMembers(parent);
		}
		for (Class<?> inter : clazz.getInterfaces())
		{
			if (FurnaceI.class.isAssignableFrom(inter))
			{
				this.loadMembers(inter);
			}
		}
		for (Field f : clazz.getDeclaredFields())
		{
			APIConst apiconst = f.getAnnotation(APIConst.class);
			if (apiconst != null)
			{
				this.props.put(apiconst.value(), new FurnaceFieldInfo(this, f, true, apiconst.value()));
				continue;
			}
			APIProperty apiprop = f.getAnnotation(APIProperty.class);
			if (apiprop != null)
			{
				this.props.put(apiprop.value(), new FurnaceFieldInfo(this, f, false, apiprop.value()));
			}
		}
		for (Method m : clazz.getDeclaredMethods())
		{
			APIFunction apifun = m.getAnnotation(APIFunction.class);
			if (apifun != null)
			{
				FurnacePropInfo prop = this.props.get(apifun.value());
				if (!(prop instanceof FurnaceFunctionInfo))
				{
					prop = new FurnaceFunctionInfo(this, apifun.value());
					this.props.put(prop.name, prop);
				}
				((FurnaceFunctionInfo) prop).methods.add(m);
				continue;
			}
			APIGetter getter = m.getAnnotation(APIGetter.class);
			APISetter setter = m.getAnnotation(APISetter.class);
			String name = null;
			if (getter != null)
			{
				name = getter.value();
			}
			if (setter != null)
			{
				name = setter.value();
			}
			if (name != null)
			{
				FurnacePropInfo prop = this.props.get(name);
				if (!(prop instanceof FurnaceGetSetInfo))
				{
					prop = new FurnaceGetSetInfo(this, name);
					this.props.put(prop.name, prop);
				}
				if (getter != null)
				{
					((FurnaceGetSetInfo) prop).getter = m;
				}
				if (setter != null)
				{
					((FurnaceGetSetInfo) prop).setter = m;
				}
			}
		}
	}
}
