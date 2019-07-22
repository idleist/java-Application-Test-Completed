<template>
  <div>
    <h2 class="title">People</h2>

    <table class="table is-stripped is-fullwidth">
      <thead>
        <tr>
          <th>Name</th>
          <th>Palindrome</th>
          <th>Authorised</th>
          <th>Enabled</th>
          <th>Colours</th>
        </tr>
      </thead>
      <tbody>
        <!--
        TODO: Step 6
        Add styles to Palindrome, Authorised and Enabled values.
        When the value is Yes the text colour should be Green.
        When the value is No the text colour should be Red.
        -->

        <tr v-for="person in people" :key="person.id">
          <td>
            <router-link
              :to="{name: 'person-edit', params: { id: person.id }}"
            >{{person | fullName}}</router-link>
          </td>
          <td>{{person | palindrome}}</td>
          <td :class="[person.authorised ? 'green-text': 'red-text']">{{person.authorised ? 'Yes' : 'No'}}</td>
          <td :class="[person.enabled ? 'green-text' : 'red-text']">{{person.enabled ? 'Yes' : 'No'}}</td>
          <td>{{person.colours | colourNames}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import { getPeople, getPerson } from '../api/people-api';

export default Vue.extend({
  async mounted() {
    this.people = await getPeople();
  },
  data() {
    const people: IPerson[] = [];

    return {
      people,

    };
  },
  filters: {
    colourNames: (colours: IColour[]): string => {
      // TODO: Step 4
      //
      // Implement the value converter function.
      // Using the colours parameter, convert the list into a comma
      // separated string of colour names. The names should be sorted
      // alphabetically and there should not be a trailing comma.
      //
      // Example: "Blue, Green, Red"

      // I didnt want to change any value in state directly, so made a copy of colours array
      const coloursCopy = [...colours];


      /*
      Sort colours array into alphabetical order based on colour name.
      The to lower case method is not necessarily needed BUT if this application grows and it
      has an option in future to add colours manually, then this will work with any user input mistakes.
      */
      coloursCopy.sort((a, b) => {
        const lowerA = a.name.toLowerCase();
        const lowerB = b.name.toLowerCase();

        return (lowerA < lowerB) ? -1 : (lowerA > lowerB) ? 1 : 0;
      });

      // initialise empty string to output
      let output = '';

      /*
      For Each loop that tracks index.  Appends the color name + , unless the last element
      in which omits the trailing comma
      */
      coloursCopy.forEach((color, index) => {

      // gives all colours capital letter.  Used for when users input new colours using the api.
      // would actually be implemented on form validation as app grows.
        const capitalizedColor = color.name.charAt(0).toUpperCase() + color.name.slice(1);

        output += (index !== coloursCopy.length - 1) ? `${capitalizedColor}, ` : capitalizedColor;
      });

      return output;
    },
    fullName: (person: IPerson): string => {
      return `${person.firstName} ${person.lastName}`;
    },
    palindrome: (person: IPerson): boolean => {

      const fullName = `${person.firstName} ${person.lastName}`;

      // TODO: Step 5
      //
      // Implement the palindrome computed field.
      // True should be returned When the FullName is spelt the same
      // forwards as it is backwards. The match should ignore any
      // spaces and should also be case insensitive.
      //
      // Example: "Bo Bob" is a palindrome.

    /*
    DISCLAIMER: I could not get this to work.  I kept getting errors based on palindrome function
    was not part of the instance.  I searched and searched for an answer (AAAGGHH!) BUT, I did not want
    to leave it unfinished.  With time I hope to learn Vue in more depth (I am getting there after all
    this googling for a solution).  I found that if I changed this filter method to return a string, then
    it worked.  Also if I took the ternary operator out of the template code, then it did print a boolean
    to the screen.  I went with that.  I hope you can stil see that my javaScript logic is correct :)
    */

      // took the space out between first and last name.  changed name to lower case
      const fullNameLowerCase = fullName.replace(/\s/g, '').toLowerCase();

      // reversed the name and assigned it to a new variable
      const fullNameReverse = fullNameLowerCase.split('').reverse().join('');


      // compared name read forwards and backwards
      if (fullNameLowerCase === fullNameReverse) {

        return true;
      }

      return false;

    },
  },

});
</script>

<style lang="sass">
  .red-text
    color: red

  .green-text
    color: green
</style>

