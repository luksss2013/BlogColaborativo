package br.com.kuka.developer;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.kuka.developer.model.Artigo;
import br.com.kuka.developer.model.Usuario;
import br.com.kuka.developer.repository.ArtigoRepository;
import br.com.kuka.developer.repository.UsuarioRepository;
import br.com.kuka.developer.service.UsuarioService;

@Controller
public class BlogController {

	public String nomeUsuario;
	public String senhaUsuario;
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRep;
	
	@Autowired ArtigoRepository artigoRep;
	
	@GetMapping("/")
	public String mostraIndex(){
		return "index";
	}
	
	//Registra usuario
	@GetMapping("/registro")
	public String mostraRegistroUsuario(Usuario usuario){
		return "formulario-registro";
	}
	
	@PostMapping("/registro")
	public String cadastraRegistroUsuario(@Valid Usuario usuario, BindingResult bindingResult){
		
		if (bindingResult.hasErrors()) {
            return "formulario-registro";
        }
		usuarioRep.save(usuario);
		return "formulario-login";
	}
	
	//Valida login usuario
	@GetMapping("/login")
	public String mostraLoginUsuario(){
		return "usuario-login";
	}
	
	@PostMapping("/login")
	public ModelAndView validaLoginUsuario(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		Usuario usuario = usuarioService.loginUsuario(username, password);
		if(usuario == null){
			return new ModelAndView("usuario-login");
		}else{
			return new ModelAndView("index");
		}
	}
	
	@GetMapping("/postar-artigo")
	public String mostraFormularioArtigo(Model model){
		model.addAttribute("artigo", new Artigo());
		return "usuario-posta";
	}
	
	@PostMapping("/postar-artigo")
	public String postarArtigo(@Valid Artigo artigo, BindingResult bindingResult, Model model){
		if (bindingResult.hasErrors()) {
            return "usuario-posta";
        }
		artigoRep.save(artigo);
		return "teste";
	}
	
	@GetMapping("/blog")
	public String mostraBlog(Model model){
		List<Artigo> artigo = (List<Artigo>) artigoRep.findAll();
		model.addAttribute("artigo", artigo);
		return "blog";
	}
	
	@GetMapping("/artigo/{id}")
	public String abreArtigo(@PathVariable long id, Model model){
		Artigo artigo = new Artigo();
		artigo = artigoRep.findOne(id);
		model.addAttribute("artigo", artigo);
		return "artigo";
	}
	
	@GetMapping("/busca-artigo")
	public String mostraBuscaArtigo(){
		return "teste";
	}
	
	@PostMapping("/busca-artigo")
	public String buscaArtigo(@RequestParam("nome-artigo") String titulo){
		if(artigoRep.findArtigoByTitulo(titulo)){
			return "teste";
		}else{
			return "NADA";
		}
	}
	
}
