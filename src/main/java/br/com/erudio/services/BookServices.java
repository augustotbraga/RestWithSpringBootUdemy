package br.com.erudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Book;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.BookRepository;

@Service
public class BookServices {
	
	@Autowired
	private BookRepository repository;
	
	public BookVO findById(Long id) {
		Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		return DozerConverter.parseObject(book, BookVO.class);
	}
	
	public Page<BookVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable); 
		return page.map(this::convertToBookVO);
	}
	
	public BookVO convertToBookVO(Book entity) {
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public BookVO create(BookVO bookVO) {
		Book book = repository.save(DozerConverter.parseObject(bookVO, Book.class));
		return DozerConverter.parseObject(book, BookVO.class);
	}
	
	public BookVO update(BookVO bookVO) {
		Book entity = repository.findById(bookVO.getIdBook()).orElseThrow(
				() -> new ResourceNotFoundException("No records found for this id"));
		entity.setAuthor(bookVO.getAuthor());;
		entity.setLaunchDate(bookVO.getLaunchDate());;
		entity.setPrice(bookVO.getPrice());
		entity.setTitle(bookVO.getTitle());
		 
		Book book = repository.save(entity);
		return DozerConverter.parseObject(book, BookVO.class);
	}
	
	public void delete(Long id) {
		Book entity = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No records found for this id"));
		repository.delete(entity);
	}

}
