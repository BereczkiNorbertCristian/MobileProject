import React from 'react';
import { StyleSheet, Text, View, Button, TextInput, FlatList, TouchableOpacity,Alert } from 'react-native';
import { StackNavigator } from 'react-navigation';
const SendIntentAndroid = require('react-native-send-intent');

class Book {
    title: string;
    author: string;
    course: string;
    email: string;
    constructor(){}
    withTitle(title){
        this.title = title;
        return this;
    }
    withAuthor(author){
        this.author = author;
        return this;
    }
    withCourse(course){
        this.course = course;
        return this;
    }
    withEmail(email){
        this.email = email;
        return this;
    }
    toString(){
        return "BOOK{"+ this.title + ", " + this.author + ", " + this.email + "}\n";
    }
}


class HomeScreen extends React.Component {
    static navigationOptions = {
        title: 'BookUSeller',
    };
    render() {
        const { navigate } = this.props.navigation;
        return (
            <View>
                <Text>Choose wisely:</Text>
                <Button
                    onPress={() => navigate('AddBook')}
                    title="Add new book"
                />
                <Button
                onPress={() => navigate('ListBooks')}
                title="List current books"
                />
            </View>
        );
    }
}

class ListBooksScreen extends React.Component {

    static navigationOptions = {
        title: 'List of Books',
    };
    constructor(props){
        super(props);
        this.books = [];
        this.books.push({bk: new Book().withAuthor('Harry').withTitle('Potter').withCourse('Maths').withEmail('bno@gmail.com')});
        this.books.push({bk: new Book().withAuthor('John').withTitle('The Old One').withCourse('English').withEmail('peter@gmail.com')});
        this.books.push({bk: new Book().withAuthor('Norbert').withTitle('El e').withCourse('CS').withEmail('berecn@amazon.com')});
    }

    goToDetails(book){
        const { navigate } = this.props.navigation;
        navigate('BookDetails',{book: book});
    }

    render(){
        return (
            <View>
                {
                    <FlatList
                        data={this.books}
                        renderItem={({item}) =>
                            <TouchableOpacity
                                style={styles.listitm}
                                onPress={() => this.goToDetails(item.bk)}
                            >
                                <Text>{item.bk.title}</Text>
                            </TouchableOpacity>
                        }
                    />
                }
            </View>
        );
    }

}

class AddBookScreen extends React.Component {
    static navigationOptions = {
        title: 'Add new book',
    };
    constructor(props){
        super(props);
        this.state = {title: 'Title', author: 'Author', course: 'Course', email: 'Email'}
    }

    submit(){
        SendIntentAndroid.sendMail("bnorbertcristian@gmail.com", "New Book!", this.prepareBody());
    }

    prepareBody(){
        let ret = "This are the details for the new book:";
        ret += "Title: " + this.state.title + "\n";
        ret += "Author: " + this.state.author + "\n";
        ret += "Course: " + this.state.course + "\n";
        ret += "Email: " + this.state.email + "\n";
        return ret;
    }

    render() {
        return (
            <View>
                <Text>
                    Title:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({title: text})}
                    value={this.state.title}
                />
                <Text>
                    Author:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({author: text})}
                    value={this.state.author}
                />
                <Text>

                    Course:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({course: text})}
                    value={this.state.course}
                />
                <Text>
                    Email:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({email: text})}
                    value={this.state.email}
                />
                <Button
                    raised
                    icon={{name: 'check'}}
                    title='SUBMIT'
                    onPress={() => this.submit()} />

            </View>
        );
    }
}

class BookDetailsScreen extends React.Component {

    static navigationOptions ={
        title: 'Details',
    };

    constructor(props){
        super(props);
        bookTmp = props.navigation.state.params.book;
        this.state = {title: bookTmp.title, author: bookTmp.author, course: bookTmp.course, email: bookTmp.email};
    }

    render() {
        return(
            <View>
                <Text>
                    Title:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({title: text})}
                    value={this.state.title}
                />
                <Text>
                    Author:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({author: text})}
                    value={this.state.author}
                />
                <Text>
                    Course:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({course: text})}
                    value={this.state.course}
                />
                <Text>
                    Email:
                </Text>
                <TextInput
                    style={{height: 40, borderColor: 'gray', borderWidth: 1}}
                    onChangeText={(text) => this.setState({email: text})}
                    value={this.state.email}
                />

            </View>
        );
    }
}

const SimpleApp = StackNavigator({
    Home: { screen: HomeScreen },
    AddBook: { screen: AddBookScreen },
    ListBooks: { screen: ListBooksScreen},
    BookDetails: { screen: BookDetailsScreen},
});

export default class App extends React.Component {
    render() {
        return <SimpleApp />;
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center'
    },
    listitm: {
        padding: 10,
        marginTop: 3,
        backgroundColor: '#d9f9b1',
        alignItems: 'center',
    }
});