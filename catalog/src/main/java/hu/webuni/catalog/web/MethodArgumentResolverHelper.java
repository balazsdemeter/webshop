package hu.webuni.catalog.web;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class MethodArgumentResolverHelper {
	
	private final PageableHandlerMethodArgumentResolver pageableResolver;

	public Pageable createPageable(Class<?> clazz, String pageableConfigurerMethodName, NativeWebRequest nativeWebRequest) {
		Method method;
		try {
			method = clazz.getMethod(pageableConfigurerMethodName, Pageable.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		MethodParameter methodParameter = new MethodParameter(method, 0);
		ModelAndViewContainer mavContainer = null;
		WebDataBinderFactory binderFactory = null;
		Pageable pageable = pageableResolver.resolveArgument(methodParameter, mavContainer, nativeWebRequest, binderFactory);
		return pageable;
	}
}