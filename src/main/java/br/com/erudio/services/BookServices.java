package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<BookVO> findAll() {
		List<Book> listBook = repository.findAll(); 
		return DozerConverter.parseListObjects(listBook, BookVO.class);
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
