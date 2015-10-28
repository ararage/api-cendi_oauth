class UrlMappings {

	static mappings = {
		"/" {
				controller = 'Oauth'
				action = [GET:'notAllowed', POST:'obtainToken', PUT:'notAllowed', DELETE:'notAllowed']
		}
	}
}
